package com.gatling

import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl.*
import io.gatling.javaapi.core.CoreDsl.*

class GetAPISimulation : Simulation() {

    override fun before() {
        println("Process the task which you want to perform before execution of load testing like auth and all")
    }

    // protocol
    private val httpProtocol = http.baseUrl("https://reqres.in/api/users")

    // scenario 1
    // Working scenario for get request with static path variable
    private val scenario1 = scenario("Get API request demo based on static value").exec(
        http("Get User Info API with Static Value")
            .get("/2")
            .check(
                jsonPath("$.data.first_name").`is`("Janet")
            )
    )

    // scenario 2
    // Working scenario for get request with dynamic path variable
    private val scenario2 = scenario("Get API request demo based on Dynamic value")
        .feed(jsonFile("user_id.json").circular())
        .exec(
            http("Get User Info API with Dynamic Value")
                .get("/#{userId}")
                .check(
                    status().shouldBe(200)
                )
        )

    // setup
    init {
        setUp(
            scenario1.injectOpen(atOnceUsers(10)),
            scenario2.injectOpen(rampUsers(5).during(2))// you can use different-different http protocol like .protocols(httpProtocol1)
        ).protocols(httpProtocol)
    }
}