# language: en

Feature: Login
  Scenario: Login to OrangeHRM
    Given I open the OrangeHRM login page
    When I enter valid username and password
    And I click the login button
    Then I should be redirected to the OrangeHRM dashboard

  Scenario: Login to OrangeHRM with invalid credentials
    Given I open the OrangeHRM login page
    When I enter invalid username or password
    And I click the login button
    Then I should see an error message indicating invalid credentials
