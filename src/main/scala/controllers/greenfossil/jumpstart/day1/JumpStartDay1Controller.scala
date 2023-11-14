package controllers.greenfossil.jumpstart.day1

import com.greenfossil.data.mapping.Mapping
import com.greenfossil.data.mapping.Mapping.*
import com.greenfossil.thorium.*
import com.linecorp.armeria.server.annotation.{Default, Get, Param, Post}

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object JumpStartDay1Controller:

  /*
   * Implement this method to returns:
   * - "Hi, {name}!" if name starts with a vowel
   * - "Hey, {name}!" if name starts with consonant
   * - "Hello stranger!" if name is empty
   */
  @Get("/greetMe")
  def greetMe(@Param @Default("") name: String) =
    val vowels = "aeiou"
    if name.isEmpty then "Hello stranger!"
    else if vowels.indexOf(Character.toLowerCase(name.charAt(0))) != -1 then s"Hi, $name!"
    else s"Hey, $name!"

/*
   * Implement this method to bind to the following fields:
   * 1. firstname : String, mandatory
   * 2. lastname: String, optional
   * 3. dob: java.time.LocalDate with format (dd/MM/yyyy)
   */
  private def signupMapping: Mapping[User] = mapping[User](
    "firstname" -> nonEmptyText(true),
    "lastname" -> optional(text),
    "dob" -> localDateUsing("dd/MM/yyyy")
  )

  /*
   * Implement this method to bind the HTTP request's body to `signupMapping`.
   * If the data mapping has validation errors, return a BadRequest with text "Invalid Data".
   * If has no validation error, return an OK with text
   *    "Welcome {firstname} {lastname}! You were born on {dob<dd/MM/yyy>}."
   */
  @Post("/signup")
  def signup(using Request) = signupMapping.bindFromRequest().fold(
    errorForm => BadRequest("Invalid Data"),
    dto =>
      val datePattern = DateTimeFormatter.ofPattern("dd/MM/yyyy")
      (dto.firstname, dto.lastname, dto.dob) match
        case (firstname, Some(lastname), dob) => Ok(s"Welcome $firstname ${lastname}! You were born on ${dob.format(datePattern)}.")
        case (firstname, None, dob) => Ok(s"Welcome $firstname! You were born on ${dob.format(datePattern)}.")
  )

case class User(
  firstname: String,
  lastname: Option[String],
  dob: LocalDate
)

