#Feature: Accessing Github APIs using RestAssured and Cucumber framework

#  Background:
#    Given I have a valid user I
Feature: Validate Github API

  @retrieve
  Scenario Outline: Validate Github GET API

    Given Github API exists
    When Github GET API is called for the id <Project>
    Then Verify the status code is "<statusCode>"

    Examples:
      |Test case description |Project				      |statusCode|
      |Valid id 		   	 |newone        		      |200	     |
      |Invalid id 		     |TestProject5537             |404	     |

  @create
  Scenario Outline: Validate Github POST API
    Given Github POST API exists
    When Github POST API is called with the name <Project>
    Then Verify the status code is "<statusCode>"

    Examples:
      |Project		  |statusCode|
      |TestRepo3570   |201       |

  @update
  Scenario Outline: Validate Github PUT API

    Given Github API exists
    When Github PUT API is called for the id <idValue> and <nameValue>
    Then Verify the status code is "<statusCode>"

    Examples:
      | Test case description   | idValue            | nameValue         | statusCode |
      |-------------------------|--------------------|-------------------|------------|
      | Valid id                | TestRepo3570       | TestRepo3570      | 200        |
      | Invalid id              | TestRepository1254 | Test_Updated      | 404        |

  @partialUpdate
  Scenario Outline: Validate Github PATCH API

    Given Github API exists
    When Github PATCH API is called for the id <idValue> and <description>
    Then Verify the status code is "<statusCode>"

    Examples:
      |Test case description   |idValue			  |description 		 		           |statusCode|
      |Valid id 		   	   |TestRepo3570      |Test Project updated by RestAssured |200	      |

  @delete
  Scenario Outline: Validate Github DELETE API

    Given Github API exists
    When Github DELETE API is called for the id <Project>
    Then Verify the status code is "<statusCode>"

    Examples:
      |Test case description |Project				      |statusCode|
      |Valid id 		     |TestRepo3570  	 		  |204	     |
      |Invalid id 		     |TestRepository142			  |404	     |
