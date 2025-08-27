Feature: Add User functionality

  Scenario: Add a new user successfully
    Given I am logged into the OrangeHRM dashboard
    When I navigate to the Add User page
    And I fill in the user details
    And I click the Save button
    Then I should see a confirmation message indicating the user was added successfully

  Scenario: Add a user with missing mandatory fields
    Given I am logged into the OrangeHRM dashboard
    When I navigate to the Add User page
    And I leave mandatory fields empty
    And I click the Save button
    Then I should see an error message indicating the mandatory fields must be filled
