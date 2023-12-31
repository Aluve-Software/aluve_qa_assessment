@book
Feature: Get Books From Book Cart

  Scenario Outline: Validate that the user can find the book id for a book with the name "<book_name>"
    Given the user has all books
    When the user searches for similar books to "<book_name>"
    Then the book "A Dance with Dragons" is in the list of similar books
    And the book price is "<price>"
    And the author is "<author>"
    And the category is "<category>"
    Examples:
      | book_name       | price | author           | category |
      |Soul of the Sword|412.00 |George R.R. Martin| Fantasy   |