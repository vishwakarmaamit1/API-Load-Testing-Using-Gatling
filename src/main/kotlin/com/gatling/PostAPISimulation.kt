package com.gatling

import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl.*

class PostAPISimulation: Simulation() {

    override fun before() {
        println("Process the task which you want to perform before execution of load testing like auth and all")
    }

    // protocol
    private val httpProtocol = http.baseUrl("https://reqres.in/api")

    // scenario 1
    // Working scenario for post request with static body
    private val scenario1 = scenario("Post API request demo with static value").exec(
        http("Get User Info API with Static Value")
            .post("/users").body(StringBody("""{"name": "morpheus", "job": "leader"}""")).asJson()
            .check(
                status().shouldBe(201),
                substring("id").exists()
            )
    )

    // scenario 2
    // Working scenario for post request with dynamic body
    private val scenario2 = scenario("Post API request demo with Dynamic value")
        .feed(jsonFile("user_name.json").circular())
        .exec(
            http("Get User Info API with Dynamic Value")
                .post("/users").body(StringBody("""{"name": "#{userName}", "job": "leader"}""")).asJson()
                .check(
                    status().shouldBe(201),
                    substring("id").exists(),
                    bodyString().saveAs("post_request_response")
                )
        ).exec{ session ->
            println("------------Post Request Response------------")
            println(session.getString("post_request_response"))
            session
        }

    // setup
    init {
        setUp(
            scenario1.injectOpen(atOnceUsers(2)).protocols(httpProtocol),
            scenario2.injectOpen(rampUsers(5).during(2)).protocols(httpProtocol)// you can use different-different http protocol like .protocols(httpProtocol1)
        )
    }
}