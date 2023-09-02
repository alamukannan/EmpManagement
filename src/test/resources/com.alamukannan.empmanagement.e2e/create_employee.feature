Feature: Create Employee

  Scenario Outline: WITH ALL REQUIRED FIELDS IS SUCCESSFUL


    When user sends employee data to be created with firstname <firstName>, lastname <lastName> and email <email>
    Then user should receive the created employee

    Examples:
      | firstName | lastName | email       |
      | alamu     | khanan   |abc@gmail.com|