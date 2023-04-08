package com.datastreaming.springwordcount

import org.apache.kafka.common.serialization.Serdes
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.apache.kafka.streams.kstream.*

@SpringBootApplication
class SpringWordCountApplication {
	@Bean
	fun lowerCaseProcessor():
			java.util.function.Consumer<KStream<String, String>> {
		return java.util.function.Consumer<KStream<String, String>>
		{ input ->
			input
				.mapValues { _, value -> value.lowercase() }
				.to("lowercasetopic")
		}
	}

	fun wordCountProcessor():
			java.util.function.Consumer<KStream<String, String>> {
		return java.util.function.Consumer<KStream<String, String>>
		{ input ->
			input
				.flatMapValues { value -> value.split(" ") }
				.peek { key, value -> println("flatMapValues WC result: ${key}:${value}") }
				.selectKey { _, value -> value }
				.peek { key, value -> println("selectKey WC result: ${key}:${value}") }
				.groupByKey(Grouped.with(Serdes.String(), Serdes.String()))
				.count()
				.toStream()
				.print(Printed.toSysOut<String, Long>().withLabel("WC result: "))
		}
	}
}

fun main(args: Array<String>) {
	runApplication<SpringWordCountApplication>(*args)
}


