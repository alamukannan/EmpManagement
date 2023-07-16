Feature: Create Employee

  Scenario: WITH ALL REQUIRED FIELDS IS SUCCESSFUL

    Given user wants to create an employee with the following attributes
      | firstName | lastName | email       |
      | alamu     | khanan   |abc@gmail.com|
    When user saves the new employee 'WITH ALL REQUIRED FIELDS'
    Then the save 'IS SUCCESSFUL'