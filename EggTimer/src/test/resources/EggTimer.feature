Feature: Test countdown functionality of EggTimer website

  Scenario Outline: Validate countdown feature
    Given user visits eggtimer.com
    When Enter number <time>
    And  click Go button
    Then validate the countdown

    Examples:
      | time				|
      | 25 seconds 			|
      | 55 seconds  		|
      | 2 minutes 40 seconds|