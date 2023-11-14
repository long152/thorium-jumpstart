package controllers.greenfossil.jumpstart.day1

import com.greenfossil.thorium.Server
import com.linecorp.armeria.client.WebClient
import com.linecorp.armeria.common.{HttpStatus, QueryParams}
import munit.FunSuite

class GreetMeSuite extends FunSuite:

  /*
   * HINT: Use the `addService` method
   */
  val server: Server = Server(8080).addServices(JumpStartDay1Controller)

  val webClient: WebClient = WebClient.of("http://localhost:8080")

  test("Get /greetMe with name that starts with a vowel"):
    val params = QueryParams.of("name", "Abraham")
    val response = webClient.get("/greetMe", params).aggregate.get()
    assertEquals(response.status(), HttpStatus.OK)
    assertNoDiff(response.contentUtf8(), "Hi, Abraham!")

  test("Get /greetMe with name that starts with a consonant"):
    val params = QueryParams.of("name", "Mona")
    val response = webClient.get("/greetMe", params).aggregate.get()
    assertEquals(response.status(), HttpStatus.OK)
    assertNoDiff(response.contentUtf8(), "Hey, Mona!")

  test("Get /greetMe with empty name"):
    val response = webClient.get("/greetMe").aggregate.get()
    assertEquals(response.status(), HttpStatus.OK)
    assertNoDiff(response.contentUtf8(), "Hello stranger!")

  test("Post /signup with invalid data"):
    val requestBody =
      """
        |{
        |  "lastname": "Tran"
        |}""".stripMargin
    val response = webClient.post("/signup", requestBody).aggregate.get()
    assertEquals(response.status(), HttpStatus.BAD_REQUEST)
    assertNoDiff(response.contentUtf8(), "Invalid Data")

  // I tried to write the test below but it didn't work (always return 400 Bad Request.)
  // Note that I've started the real server and it works fine.
  // Please help me check where I was wrong. Thanks!
//  test("Post /signup with valid data"):
//    val body =
//      """
//        |{
//        |  "firstname": "Long",
//        |  "lastname": "Tran",
//        |  "dob": "15/02/1994"
//        |}""".stripMargin
//    val response = webClient.post("/signup", body).aggregate.get()
//    assertEquals(response.status(), HttpStatus.OK)
//    assertNoDiff(response.contentUtf8(), "Welcome Long Tran! You were born on 15/02/1994.")

  override def beforeAll(): Unit = server.start()

  override def afterAll(): Unit = server.stop()