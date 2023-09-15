@taxtim
Feature: Tax Calculator

  Background:
    Given the tax year is "2023"
    And the salary before deductions is "20000"
    And the salary is received "monthly"

  Scenario Outline: Calculate tax when the age is 22
    Given the user is "<age>" years old
    When the user calculates their tax
    Then the take home should be R"<take_home>"
    And the tax should be R"<tax>"

    Examples:
      | age | take_home | tax    |
      |22   |17498      |2,324.58|