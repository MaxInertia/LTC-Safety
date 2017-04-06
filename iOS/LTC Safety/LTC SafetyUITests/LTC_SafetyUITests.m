//
//  LTC_SafetyUITests.m
//  LTC SafetyUITests
//
//  Created by Allan Kerr on 2017-01-26.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XCTest/XCTest.h>
#import <Foundation/Foundation.h>
#import <time.h>

@interface LTC_SafetyUITests : XCTestCase

@end

@implementation LTC_SafetyUITests

- (void)setUp {
    [super setUp];
    [[[XCUIApplication alloc] init] launch];
}

- (void)tearDown {
    [super tearDown];
    [[[XCUIApplication alloc] init] terminate];
}
/**
 Tests that a concern may be submitted without crashing
 */
- (void)testValid {
  
    
    XCUIApplication *app = [[XCUIApplication alloc] init];
    [app.buttons[@"New Safety Concern"] tap];
    
    XCUIElementQuery *tablesQuery = app.tables;
    XCUIElement *nameTextField = tablesQuery.textFields[@"First and Last Name"];
    [nameTextField tap];
    
    [nameTextField typeText:@"Test Tan"];
    XCTAssert(tablesQuery.textFields[@"Test Tan"].exists);
    XCUIElement *phoneNumberTextField = tablesQuery.textFields[@"Phone Number"];
    [phoneNumberTextField tap];
    
    XCUIElement *moreNumbersKey = app.keys[@"more, numbers"];
    [moreNumbersKey tap];
    [phoneNumberTextField typeText:@"3062912761"];
    XCTAssert(tablesQuery.textFields[@"3062912761"].exists);
    XCUIElement *emailTextField = tablesQuery.textFields[@"Email Address"];
    [emailTextField tap];
    [emailTextField typeText:@"Fake"];
    [moreNumbersKey tap];
    emailTextField = tablesQuery.textFields[@"Fake"];
    [emailTextField typeText:@"@"];
    
    XCUIElement *moreLettersKey = app.keys[@"more, letters"];
    [moreLettersKey tap];
    emailTextField = tablesQuery.textFields[@"Fake@"];
    [emailTextField typeText:@"no"];
    XCTAssert(tablesQuery.textFields[@"Fake@no"].exists);
    [tablesQuery.staticTexts[@"Nature of Concern"] tap];
    [tablesQuery.staticTexts[@"No Response to Requests"] tap];
    [tablesQuery.staticTexts[@"Facility"] tap];
    [tablesQuery.staticTexts[@"Preston Extendicare"] tap];
    [tablesQuery.textFields[@"Room"] tap];
    [moreNumbersKey tap];
    XCUIElement *roomTextField = tablesQuery.textFields[@"Room"];
    [roomTextField typeText:@"45"];
        XCTAssert(tablesQuery.textFields[@"45"].exists);
    XCUIElement *textView = [[[tablesQuery childrenMatchingType:XCUIElementTypeCell] elementBoundByIndex:6] childrenMatchingType:XCUIElementTypeTextView].element;
    [textView tap];
    [textView typeText:@"None"];
        XCTAssert(tablesQuery.textViews[@"None"].exists);
    //[[[[[app childrenMatchingType:XCUIElementTypeWindow] elementBoundByIndex:0] childrenMatchingType:XCUIElementTypeOther].element childrenMatchingType:XCUIElementTypeOther].element tap];
    [tablesQuery.staticTexts[@"Submit Concern"] tap];
    
    
    // Use recording to get started writing UI tests.
    // Use XCTAssert and related functions to verify your tests produce the correct results.
}

/**
 Tests that the submission is rejected if both email and phone are missing
 */
- (void)testNullEmailPhone {
    
    
    XCUIApplication *app = [[XCUIApplication alloc] init];
    [app.buttons[@"New Safety Concern"] tap];
    
    XCUIElementQuery *tablesQuery = app.tables;
    XCUIElement *nameTextField = tablesQuery.textFields[@"First and Last Name"];
    [nameTextField tap];
    
    [nameTextField typeText:@"Test Tan"];
    
    
    XCUIElement *moreNumbersKey = app.keys[@"more, numbers"];
    
    [tablesQuery.staticTexts[@"Nature of Concern"] tap];
    [tablesQuery.staticTexts[@"No Response to Requests"] tap];
    // Failed to find matching element please file bug (bugreport.apple.com) and provide output from Console.app
    [tablesQuery.staticTexts[@"Facility"] tap];
    [tablesQuery.staticTexts[@"Preston Extendicare"] tap];
    [tablesQuery.textFields[@"Room"] tap];
    [moreNumbersKey tap];
    XCUIElement *roomTextField = tablesQuery.textFields[@"Room"];
    [roomTextField typeText:@"45"];
    
    XCUIElement *textView = [[[tablesQuery childrenMatchingType:XCUIElementTypeCell] elementBoundByIndex:6] childrenMatchingType:XCUIElementTypeTextView].element;
    [textView tap];
    [textView typeText:@"None"];
    //[[[[[app childrenMatchingType:XCUIElementTypeWindow] elementBoundByIndex:0] childrenMatchingType:XCUIElementTypeOther].element childrenMatchingType:XCUIElementTypeOther].element tap];
    [tablesQuery.staticTexts[@"Submit Concern"] tap];
    
    
    // Use recording to get started writing UI tests.
    // Use XCTAssert and related functions to verify your tests produce the correct results.
    // Use XCTAssert and related functions to verify your tests produce the correct results.
    NSPredicate *exists = [NSPredicate predicateWithFormat:@"exists == 1"];
    XCUIElement *test = app.alerts.element.staticTexts[@"Either a phone number or an email address must be provided when submitting a concern." ];
    // perform async loop, waiting for the object specified to appear
    [self expectationForPredicate:exists evaluatedWithObject:test handler:nil];
    [self waitForExpectationsWithTimeout:15 handler:nil];

    XCTAssert(app.alerts.element.staticTexts[@"Either a phone number or an email address must be provided when submitting a concern." ].exists);
}

/**
 Tests that concern is rejected if name is missing
 */
- (void)testNullName {
    
    
    XCUIApplication *app = [[XCUIApplication alloc] init];
    [app.buttons[@"New Safety Concern"] tap];
    
    XCUIElementQuery *tablesQuery = app.tables;
    
    
    XCUIElement *phoneNumberTextField = tablesQuery.textFields[@"Phone Number"];
    [phoneNumberTextField tap];
    
    XCUIElement *moreNumbersKey = app.keys[@"more, numbers"];
    [moreNumbersKey tap];
    [phoneNumberTextField typeText:@"3062912761"];
    XCUIElement *emailTextField = tablesQuery.textFields[@"Email Address"];
    [emailTextField tap];
    [emailTextField typeText:@"Fake"];
    [moreNumbersKey tap];
    emailTextField = tablesQuery.textFields[@"Fake"];
    [emailTextField typeText:@"@"];
    
    XCUIElement *moreLettersKey = app.keys[@"more, letters"];
    [moreLettersKey tap];
    emailTextField = tablesQuery.textFields[@"Fake@"];
    [emailTextField typeText:@"no"];
    
    [tablesQuery.staticTexts[@"Nature of Concern"] tap];
    [tablesQuery.staticTexts[@"No Response to Requests"] tap];
    [tablesQuery.staticTexts[@"Facility"] tap];
    [tablesQuery.staticTexts[@"Preston Extendicare"] tap];
    [tablesQuery.textFields[@"Room"] tap];
    [moreNumbersKey tap];
    XCUIElement *roomTextField = tablesQuery.textFields[@"Room"];
    [roomTextField typeText:@"45"];
    
    XCUIElement *textView = [[[tablesQuery childrenMatchingType:XCUIElementTypeCell] elementBoundByIndex:6] childrenMatchingType:XCUIElementTypeTextView].element;
    [textView tap];
    [textView typeText:@"None"];
    //[[[[[app childrenMatchingType:XCUIElementTypeWindow] elementBoundByIndex:0] childrenMatchingType:XCUIElementTypeOther].element childrenMatchingType:XCUIElementTypeOther].element tap];
    [tablesQuery.staticTexts[@"Submit Concern"] tap];
    
    
    NSPredicate *exists = [NSPredicate predicateWithFormat:@"exists == 1"];
    XCUIElement *test = app.alerts.element.staticTexts[@"A first and last name must be provided when submitting a concern." ];
    // perform async loop, waiting for the object specified to appear
    [self expectationForPredicate:exists evaluatedWithObject:test handler:nil];
    [self waitForExpectationsWithTimeout:15 handler:nil];
    // Use recording to get started writing UI tests.
    // Use XCTAssert and related functions to verify your tests produce the correct results.
    
    XCTAssert(app.alerts.element.staticTexts[@"A first and last name must be provided when submitting a concern." ].exists);
}

/**
 Tests that concern is accepted if email is missing but phone number is not
 */
- (void)testNullEmail {
    
    
    XCUIApplication *app = [[XCUIApplication alloc] init];
    [app.buttons[@"New Safety Concern"] tap];
    
    XCUIElementQuery *tablesQuery = app.tables;
    XCUIElement *nameTextField = tablesQuery.textFields[@"First and Last Name"];
    [nameTextField tap];
    
    [nameTextField typeText:@"Test Tan"];
    
    XCUIElement *phoneNumberTextField = tablesQuery.textFields[@"Phone Number"];
    [phoneNumberTextField tap];
    
    XCUIElement *moreNumbersKey = app.keys[@"more, numbers"];
    [moreNumbersKey tap];
    [phoneNumberTextField typeText:@"3062912761"];
    
    [tablesQuery.staticTexts[@"Nature of Concern"] tap];
    [tablesQuery.staticTexts[@"No Response to Requests"] tap];
    // Failed to find matching element please file bug (bugreport.apple.com) and provide output from Console.app
    [tablesQuery.staticTexts[@"Facility"] tap];
    [tablesQuery.staticTexts[@"Preston Extendicare"] tap];
    [tablesQuery.textFields[@"Room"] tap];
    [moreNumbersKey tap];
    XCUIElement *roomTextField = tablesQuery.textFields[@"Room"];
    [roomTextField typeText:@"45"];
    
    XCUIElement *textView = [[[tablesQuery childrenMatchingType:XCUIElementTypeCell] elementBoundByIndex:6] childrenMatchingType:XCUIElementTypeTextView].element;
    [textView tap];
    [textView typeText:@"None"];
    //[[[[[app childrenMatchingType:XCUIElementTypeWindow] elementBoundByIndex:0] childrenMatchingType:XCUIElementTypeOther].element childrenMatchingType:XCUIElementTypeOther].element tap];
    [tablesQuery.staticTexts[@"Submit Concern"] tap];
    
    
    // Use recording to get started writing UI tests.
    // Use XCTAssert and related functions to verify your tests produce the correct results.
}

/**
 Tests that concern is accepted if phone is missing but email is not
 */
- (void)testNullPhone {
    
    
    XCUIApplication *app = [[XCUIApplication alloc] init];
    [app.buttons[@"New Safety Concern"] tap];
    
    XCUIElementQuery *tablesQuery = app.tables;
    XCUIElement *nameTextField = tablesQuery.textFields[@"First and Last Name"];
    [nameTextField tap];
    
    [nameTextField typeText:@"Test Tan"];

    
    XCUIElement *moreNumbersKey = app.keys[@"more, numbers"];
        XCUIElement *emailTextField = tablesQuery.textFields[@"Email Address"];
    [emailTextField tap];
    [emailTextField typeText:@"Fake"];
    [moreNumbersKey tap];
    emailTextField = tablesQuery.textFields[@"Fake"];
    [emailTextField typeText:@"@"];
    
    XCUIElement *moreLettersKey = app.keys[@"more, letters"];
    [moreLettersKey tap];
    emailTextField = tablesQuery.textFields[@"Fake@"];
    [emailTextField typeText:@"no"];
    
    [tablesQuery.staticTexts[@"Nature of Concern"] tap];
    [tablesQuery.staticTexts[@"No Response to Requests"] tap];
    // Failed to find matching element please file bug (bugreport.apple.com) and provide output from Console.app
    [tablesQuery.staticTexts[@"Facility"] tap];
    [tablesQuery.staticTexts[@"Preston Extendicare"] tap];
    [tablesQuery.textFields[@"Room"] tap];
    [moreNumbersKey tap];
    XCUIElement *roomTextField = tablesQuery.textFields[@"Room"];
    [roomTextField typeText:@"45"];
    
    XCUIElement *textView = [[[tablesQuery childrenMatchingType:XCUIElementTypeCell] elementBoundByIndex:6] childrenMatchingType:XCUIElementTypeTextView].element;
    [textView tap];
    [textView typeText:@"None"];
    //[[[[[app childrenMatchingType:XCUIElementTypeWindow] elementBoundByIndex:0] childrenMatchingType:XCUIElementTypeOther].element childrenMatchingType:XCUIElementTypeOther].element tap];
    [tablesQuery.staticTexts[@"Submit Concern"] tap];
    
    
    // Use recording to get started writing UI tests.
    // Use XCTAssert and related functions to verify your tests produce the correct results.
}

/**
 Tests that submission is rejected if no concern is given
 */
- (void)testNullConcern {
    
    
    XCUIApplication *app = [[XCUIApplication alloc] init];
    [app.buttons[@"New Safety Concern"] tap];
    
    XCUIElementQuery *tablesQuery = app.tables;
    XCUIElement *nameTextField = tablesQuery.textFields[@"First and Last Name"];
    [nameTextField tap];
    
    [nameTextField typeText:@"Test Tan"];
    
    XCUIElement *phoneNumberTextField = tablesQuery.textFields[@"Phone Number"];
    [phoneNumberTextField tap];
    
    XCUIElement *moreNumbersKey = app.keys[@"more, numbers"];
    [moreNumbersKey tap];
    [phoneNumberTextField typeText:@"3062912761"];
    XCUIElement *emailTextField = tablesQuery.textFields[@"Email Address"];
    [emailTextField tap];
    [emailTextField typeText:@"Fake"];
    [moreNumbersKey tap];
    emailTextField = tablesQuery.textFields[@"Fake"];
    [emailTextField typeText:@"@"];
    
    XCUIElement *moreLettersKey = app.keys[@"more, letters"];
    [moreLettersKey tap];
    emailTextField = tablesQuery.textFields[@"Fake@"];
    [emailTextField typeText:@"no"];
    // Failed to find matching element please file bug (bugreport.apple.com) and provide output from Console.app
    [tablesQuery.staticTexts[@"Facility"] tap];
    [tablesQuery.staticTexts[@"Preston Extendicare"] tap];
    [tablesQuery.textFields[@"Room"] tap];
    [moreNumbersKey tap];
    XCUIElement *roomTextField = tablesQuery.textFields[@"Room"];
    [roomTextField typeText:@"45"];
    
    XCUIElement *textView = [[[tablesQuery childrenMatchingType:XCUIElementTypeCell] elementBoundByIndex:6] childrenMatchingType:XCUIElementTypeTextView].element;
    [textView tap];
    [textView typeText:@"None"];
    //[[[[[app childrenMatchingType:XCUIElementTypeWindow] elementBoundByIndex:0] childrenMatchingType:XCUIElementTypeOther].element childrenMatchingType:XCUIElementTypeOther].element tap];
    [tablesQuery.staticTexts[@"Submit Concern"] tap];
    
    // Use recording to get started writing UI tests.
    // Use XCTAssert and related functions to verify your tests produce the correct results.
    NSPredicate *exists = [NSPredicate predicateWithFormat:@"exists == 1"];
    XCUIElement *test = app.alerts.element.staticTexts[@"The nature of the concern must be specified when a concern is submitted" ];
    // perform async loop, waiting for the object specified to appear
    [self expectationForPredicate:exists evaluatedWithObject:test handler:nil];
    [self waitForExpectationsWithTimeout:15 handler:nil];
    XCTAssert(app.alerts.element.staticTexts[@"The nature of the concern must be specified when a concern is submitted" ].exists);
}

/**
 Tests that submission is rejected if no facility is given
 */
- (void)testNullFacility {
    
    
    XCUIApplication *app = [[XCUIApplication alloc] init];
    [app.buttons[@"New Safety Concern"] tap];
    
    XCUIElementQuery *tablesQuery = app.tables;
    XCUIElement *nameTextField = tablesQuery.textFields[@"First and Last Name"];
    [nameTextField tap];
    
    [nameTextField typeText:@"Test Tan"];
    
    XCUIElement *phoneNumberTextField = tablesQuery.textFields[@"Phone Number"];
    [phoneNumberTextField tap];
    
    XCUIElement *moreNumbersKey = app.keys[@"more, numbers"];
    [moreNumbersKey tap];
    [phoneNumberTextField typeText:@"3062912761"];
    XCUIElement *emailTextField = tablesQuery.textFields[@"Email Address"];
    [emailTextField tap];
    [emailTextField typeText:@"Fake"];
    [moreNumbersKey tap];
    emailTextField = tablesQuery.textFields[@"Fake"];
    [emailTextField typeText:@"@"];
    
    XCUIElement *moreLettersKey = app.keys[@"more, letters"];
    [moreLettersKey tap];
    emailTextField = tablesQuery.textFields[@"Fake@"];
    [emailTextField typeText:@"no"];
    [tablesQuery.staticTexts[@"Nature of Concern"] tap];
    [tablesQuery.staticTexts[@"No Response to Requests"] tap];
    // Failed to find matching element please file bug (bugreport.apple.com) and provide output from Console.app
    [tablesQuery.textFields[@"Room"] tap];
    [moreNumbersKey tap];
    XCUIElement *roomTextField = tablesQuery.textFields[@"Room"];
    [roomTextField typeText:@"45"];
    
    XCUIElement *textView = [[[tablesQuery childrenMatchingType:XCUIElementTypeCell] elementBoundByIndex:6] childrenMatchingType:XCUIElementTypeTextView].element;
    [textView tap];
    [textView typeText:@"None"];
    //[[[[[app childrenMatchingType:XCUIElementTypeWindow] elementBoundByIndex:0] childrenMatchingType:XCUIElementTypeOther].element childrenMatchingType:XCUIElementTypeOther].element tap];
    [tablesQuery.staticTexts[@"Submit Concern"] tap];
    
    
    // Use recording to get started writing UI tests.
    // Use XCTAssert and related functions to verify your tests produce the correct results.
    NSPredicate *exists = [NSPredicate predicateWithFormat:@"exists == 1"];
    XCUIElement *test = app.alerts.element.staticTexts[@"A facility name must be provided when submitting a concern." ];
    // perform async loop, waiting for the object specified to appear
    [self expectationForPredicate:exists evaluatedWithObject:test handler:nil];
    [self waitForExpectationsWithTimeout:15 handler:nil];
    XCTAssert(app.alerts.element.staticTexts[@"A facility name must be provided when submitting a concern." ].exists);
}

/**
 Tests that submission is accepted if room is null
 */
- (void)testNullRoom {
    
    
    XCUIApplication *app = [[XCUIApplication alloc] init];
    [app.buttons[@"New Safety Concern"] tap];
    
    XCUIElementQuery *tablesQuery = app.tables;
    XCUIElement *nameTextField = tablesQuery.textFields[@"First and Last Name"];
    [nameTextField tap];
    
    [nameTextField typeText:@"Test Tan"];
    
    XCUIElement *phoneNumberTextField = tablesQuery.textFields[@"Phone Number"];
    [phoneNumberTextField tap];
    
    XCUIElement *moreNumbersKey = app.keys[@"more, numbers"];
    [moreNumbersKey tap];
    [phoneNumberTextField typeText:@"3062912761"];
    XCUIElement *emailTextField = tablesQuery.textFields[@"Email Address"];
    [emailTextField tap];
    [emailTextField typeText:@"Fake"];
    [moreNumbersKey tap];
    emailTextField = tablesQuery.textFields[@"Fake"];
    [emailTextField typeText:@"@"];
    
    XCUIElement *moreLettersKey = app.keys[@"more, letters"];
    [moreLettersKey tap];
    emailTextField = tablesQuery.textFields[@"Fake@"];
    [emailTextField typeText:@"no"];
    [tablesQuery.staticTexts[@"Nature of Concern"] tap];
    [tablesQuery.staticTexts[@"No Response to Requests"] tap];
    // Failed to find matching element please file bug (bugreport.apple.com) and provide output from Console.app
    [tablesQuery.staticTexts[@"Facility"] tap];
    [tablesQuery.staticTexts[@"Preston Extendicare"] tap];
    [tablesQuery.textFields[@"Room"] tap];
    [moreNumbersKey tap];
    
    XCUIElement *textView = [[[tablesQuery childrenMatchingType:XCUIElementTypeCell] elementBoundByIndex:6] childrenMatchingType:XCUIElementTypeTextView].element;
    [textView tap];
    [textView typeText:@"None"];
    //[[[[[app childrenMatchingType:XCUIElementTypeWindow] elementBoundByIndex:0] childrenMatchingType:XCUIElementTypeOther].element childrenMatchingType:XCUIElementTypeOther].element tap];
    [tablesQuery.staticTexts[@"Submit Concern"] tap];
    
    
    // Use recording to get started writing UI tests.
    // Use XCTAssert and related functions to verify your tests produce the correct results.
}

/**
 Tests that submission is accepted if actions is null
 */
- (void)testNullActions {
    
    
    XCUIApplication *app = [[XCUIApplication alloc] init];
    [app.buttons[@"New Safety Concern"] tap];
    
    XCUIElementQuery *tablesQuery = app.tables;
    XCUIElement *nameTextField = tablesQuery.textFields[@"First and Last Name"];
    [nameTextField tap];
    
    [nameTextField typeText:@"Test Tan"];
    
    XCUIElement *phoneNumberTextField = tablesQuery.textFields[@"Phone Number"];
    [phoneNumberTextField tap];
    
    XCUIElement *moreNumbersKey = app.keys[@"more, numbers"];
    [moreNumbersKey tap];
    [phoneNumberTextField typeText:@"3062912761"];
    XCUIElement *emailTextField = tablesQuery.textFields[@"Email Address"];
    [emailTextField tap];
    [emailTextField typeText:@"Fake"];
    [moreNumbersKey tap];
    emailTextField = tablesQuery.textFields[@"Fake"];
    [emailTextField typeText:@"@"];
    
    XCUIElement *moreLettersKey = app.keys[@"more, letters"];
    [moreLettersKey tap];
    emailTextField = tablesQuery.textFields[@"Fake@"];
    [emailTextField typeText:@"no"];
    [tablesQuery.staticTexts[@"Nature of Concern"] tap];
    [tablesQuery.staticTexts[@"No Response to Requests"] tap];
    // Failed to find matching element please file bug (bugreport.apple.com) and provide output from Console.app
    [tablesQuery.staticTexts[@"Facility"] tap];
    [tablesQuery.staticTexts[@"Preston Extendicare"] tap];
    [tablesQuery.textFields[@"Room"] tap];
    [moreNumbersKey tap];
    XCUIElement *roomTextField = tablesQuery.textFields[@"Room"];
    [roomTextField typeText:@"45"];
    
    //[[[[[app childrenMatchingType:XCUIElementTypeWindow] elementBoundByIndex:0] childrenMatchingType:XCUIElementTypeOther].element childrenMatchingType:XCUIElementTypeOther].element tap];
    [tablesQuery.staticTexts[@"Submit Concern"] tap];
    
    
    // Use recording to get started writing UI tests.
    // Use XCTAssert and related functions to verify your tests produce the correct results.
}


/**
 Tests the navigation between two created concerns
 , checks that all values are correct
 */
- (void)testOldConcernNavigation {
    
    //Initiates the app
    XCUIApplication *app = [[XCUIApplication alloc] init];
    
    //Creates a new concern
    [app.buttons[@"New Safety Concern"] tap];
    
    XCUIElementQuery *tablesQuery = app.tables;
    
    //Enter new name "Nav test1"
    XCUIElement *nameTextField = tablesQuery.textFields[@"First and Last Name"];
    [nameTextField tap];
    [nameTextField typeText:@"Nav test1"];
    //Enters new Phone Number "3062912761"
    XCUIElement *phoneNumberTextField = tablesQuery.textFields[@"Phone Number"];
    [phoneNumberTextField tap];
    XCUIElement *moreNumbersKey = app.keys[@"more, numbers"];
    [moreNumbersKey tap];
    [phoneNumberTextField typeText:@"3062912761"];
    //Enters new email "Fake@no"
    XCUIElement *emailTextField = tablesQuery.textFields[@"Email Address"];
    [emailTextField tap];
    [emailTextField typeText:@"Fake"];
    [moreNumbersKey tap];
    emailTextField = tablesQuery.textFields[@"Fake"];
    [emailTextField typeText:@"@"];
    XCUIElement *moreLettersKey = app.keys[@"more, letters"];
    [moreLettersKey tap];
    emailTextField = tablesQuery.textFields[@"Fake@"];
    [emailTextField typeText:@"no"];
    //Enters new concern "No Response to Requests"
    [tablesQuery.staticTexts[@"Nature of Concern"] tap];
    [tablesQuery.staticTexts[@"No Response to Requests"] tap];
    //Enters new facility "Preston Extendicare"
    [tablesQuery.staticTexts[@"Facility"] tap];
    [tablesQuery.staticTexts[@"Preston Extendicare"] tap];
    //Enters new room "45"
    [tablesQuery.textFields[@"Room"] tap];
    [moreNumbersKey tap];
    XCUIElement *roomTextField = tablesQuery.textFields[@"Room"];
    [roomTextField typeText:@"45"];
    //Enters new actions taken "none"
    XCUIElement *textView = [[[tablesQuery childrenMatchingType:XCUIElementTypeCell] elementBoundByIndex:6] childrenMatchingType:XCUIElementTypeTextView].element;
    [textView tap];
    [textView typeText:@"None"];
    //Submits concern
    [tablesQuery.staticTexts[@"Submit Concern"] tap];
    
    XCUIElementQuery *query = [app.tables containingType:XCUIElementTypeTable identifier:@"LTCConcernTableView"];

    //Navigates to the new concerns page
    [[query.cells elementBoundByIndex:0] tap];
    //Gos back to the main menu
    [app.navigationBars[@"View Concern"].buttons[@"LTC Safety"] tap];
    
    //Creates new concern
    [app.buttons[@"New Safety Concern"] tap];
    XCUIElementQuery *tablesQuery2 = app.tables;
    //Enter new name "Nav test2"
    XCUIElement *nameTextField2 = tablesQuery2.textFields[@"First and Last Name"];
    [nameTextField2 tap];
    [nameTextField2 typeText:@"Nav test2"];
    //Enters new Phone Number "3061111111"
    XCUIElement *phoneNumberTextField2 = tablesQuery2.textFields[@"Phone Number"];
    [phoneNumberTextField2 tap];
    XCUIElement *moreNumbersKey2 = app.keys[@"more, numbers"];
    [moreNumbersKey2 tap];
    [phoneNumberTextField2 typeText:@"3061111111"];
    //Enters new email "Fake@no"
    XCUIElement *emailTextField2 = tablesQuery2.textFields[@"Email Address"];
    [emailTextField2 tap];
    [emailTextField2 typeText:@"Fake2"];
    [moreNumbersKey2 tap];
    emailTextField2 = tablesQuery2.textFields[@"Fake2"];
    [emailTextField2 typeText:@"@"];
    XCUIElement *moreLettersKey2 = app.keys[@"more, letters"];
    [moreLettersKey2 tap];
    emailTextField2 = tablesQuery2.textFields[@"Fake2@"];
    [emailTextField2 typeText:@"no"];
    //Enters new concern "Infection Control"
    [tablesQuery2.staticTexts[@"Nature of Concern"] tap];
    [tablesQuery2.staticTexts[@"Infection Control"] tap];
    //Enters new facility "Oliver Place"
    [tablesQuery2.staticTexts[@"Facility"] tap];
    [tablesQuery2.staticTexts[@"Oliver Place"] tap];
    //Enters new room "54"
    [tablesQuery2.textFields[@"Room"] tap];
    [moreNumbersKey2 tap];
    XCUIElement *roomTextField2 = tablesQuery2.textFields[@"Room"];
    [roomTextField2 typeText:@"54"];
    //Enters new actions taken "none"
    XCUIElement *textView2 = [[[tablesQuery2 childrenMatchingType:XCUIElementTypeCell] elementBoundByIndex:6] childrenMatchingType:XCUIElementTypeTextView].element;
    [textView2 tap];
    [textView2 typeText:@"None"];
    //Submits the concern
    [tablesQuery2.staticTexts[@"Submit Concern"] tap];
    
    //Navigates to the new concerns page
    
    [[query.cells elementBoundByIndex:0] tap];
    
    //Gos back to the main menu
    [app.navigationBars[@"View Concern"].buttons[@"LTC Safety"] tap];

    //Navigates to the first concern created
    [[query.cells elementBoundByIndex:1] tap];
    
    //Asserts that all the information in the concern is correct
    XCTAssert(tablesQuery2.staticTexts[@"Nav test1"].exists);
    XCTAssert(tablesQuery2.staticTexts[@"Fake@no"].exists);
    XCTAssert(tablesQuery2.staticTexts[@"3062912761"].exists);
    XCTAssert(tablesQuery2.staticTexts[@"No Response to Requests"].exists);
    XCTAssert(tablesQuery2.staticTexts[@"Preston Extendicare"].exists);
    XCTAssert(tablesQuery2.staticTexts[@"45"].exists);
    XCTAssert(tablesQuery2.staticTexts[@"Pending"].exists);
}

/**
 Submits a concern then retracts it and checks its status
 */
- (void)testRetract {
    
    //Initiates the app
    XCUIApplication *app = [[XCUIApplication alloc] init];
    
    //Creates a new concern
    [app.buttons[@"New Safety Concern"] tap];
    
    XCUIElementQuery *tablesQuery = app.tables;
    
    //Enter new name "retract test1"
    XCUIElement *nameTextField = tablesQuery.textFields[@"First and Last Name"];
    [nameTextField tap];
    [nameTextField typeText:@"retract test1"];
    //Enters new Phone Number "3062912761"
    XCUIElement *phoneNumberTextField = tablesQuery.textFields[@"Phone Number"];
    [phoneNumberTextField tap];
    XCUIElement *moreNumbersKey = app.keys[@"more, numbers"];
    [moreNumbersKey tap];
    [phoneNumberTextField typeText:@"3062912761"];
    //Enters new email "Fake@no"
    XCUIElement *emailTextField = tablesQuery.textFields[@"Email Address"];
    [emailTextField tap];
    [emailTextField typeText:@"Fake"];
    [moreNumbersKey tap];
    emailTextField = tablesQuery.textFields[@"Fake"];
    [emailTextField typeText:@"@"];
    XCUIElement *moreLettersKey = app.keys[@"more, letters"];
    [moreLettersKey tap];
    emailTextField = tablesQuery.textFields[@"Fake@"];
    [emailTextField typeText:@"no"];
    //Enters new concern "No Response to Requests"
    [tablesQuery.staticTexts[@"Nature of Concern"] tap];
    [tablesQuery.staticTexts[@"No Response to Requests"] tap];
    //Enters new facility "Preston Extendicare"
    [tablesQuery.staticTexts[@"Facility"] tap];
    [tablesQuery.staticTexts[@"Preston Extendicare"] tap];
    //Enters new room "45"
    [tablesQuery.textFields[@"Room"] tap];
    [moreNumbersKey tap];
    XCUIElement *roomTextField = tablesQuery.textFields[@"Room"];
    [roomTextField typeText:@"45"];
    //Enters new actions taken "none"
    XCUIElement *textView = [[[tablesQuery childrenMatchingType:XCUIElementTypeCell] elementBoundByIndex:6] childrenMatchingType:XCUIElementTypeTextView].element;
    [textView tap];
    [textView typeText:@"None"];
    //Submits concern
    [tablesQuery.staticTexts[@"Submit Concern"] tap];
    
    
    XCUIElementQuery *query = [app.tables containingType:XCUIElementTypeTable identifier:@"LTCConcernTableView"];
    
    //Navigates to the new concerns page
    [[query.cells elementBoundByIndex:0] tap];
    
    //Retract the concern
    [tablesQuery.staticTexts[@"Retract"] tap];
    
    //Navigates back to the new concerns page
    [[query.cells elementBoundByIndex:0] tap];

    //Checks that the concern is correctly retracted
    XCTAssert(tablesQuery.staticTexts[@"Retracted"].exists);
}

/**
 Test case for testing that the expected statuses are shown for a concern when the refresh concerns button is pressed.
 */
- (void)testRefresh {

    //Initiates the app
    XCUIApplication *app = [[XCUIApplication alloc] init];
    
    //Creates a new concern
    [app.buttons[@"New Safety Concern"] tap];
    
    XCUIElementQuery *tablesQuery = app.tables;
    
    //Enter new name "retract test1"
    XCUIElement *nameTextField = tablesQuery.textFields[@"First and Last Name"];
    [nameTextField tap];
    [nameTextField typeText:@"refresh test1"];
    //Enters new Phone Number "3062912761"
    XCUIElement *phoneNumberTextField = tablesQuery.textFields[@"Phone Number"];
    [phoneNumberTextField tap];
    XCUIElement *moreNumbersKey = app.keys[@"more, numbers"];
    [moreNumbersKey tap];
    [phoneNumberTextField typeText:@"3062912761"];
    //Enters new email "Fake@no"
    XCUIElement *emailTextField = tablesQuery.textFields[@"Email Address"];
    [emailTextField tap];
    [emailTextField typeText:@"Fake"];
    [moreNumbersKey tap];
    emailTextField = tablesQuery.textFields[@"Fake"];
    [emailTextField typeText:@"@"];
    XCUIElement *moreLettersKey = app.keys[@"more, letters"];
    [moreLettersKey tap];
    emailTextField = tablesQuery.textFields[@"Fake@"];
    [emailTextField typeText:@"no"];
    //Enters new concern "No Response to Requests"
    [tablesQuery.staticTexts[@"Nature of Concern"] tap];
    [tablesQuery.staticTexts[@"No Response to Requests"] tap];
    //Enters new facility "Preston Extendicare"
    [tablesQuery.staticTexts[@"Facility"] tap];
    [tablesQuery.staticTexts[@"Preston Extendicare"] tap];
    //Enters new room "45"
    [tablesQuery.textFields[@"Room"] tap];
    [moreNumbersKey tap];
    XCUIElement *roomTextField = tablesQuery.textFields[@"Room"];
    [roomTextField typeText:@"45"];
    //Enters new actions taken "none"
    XCUIElement *textView = [[[tablesQuery childrenMatchingType:XCUIElementTypeCell] elementBoundByIndex:6] childrenMatchingType:XCUIElementTypeTextView].element;
    [textView tap];
    [textView typeText:@"None"];
    //Submits concern
    [tablesQuery.staticTexts[@"Submit Concern"] tap];
    
    [[[XCUIApplication alloc] init].navigationBars[@"LTC Safety"].buttons[@"Refresh"] tap];
    
    XCUIElementQuery *query = [app.tables containingType:XCUIElementTypeTable identifier:@"LTCConcernTableView"];
    
    //Navigates to the new concerns page
    [[query.cells elementBoundByIndex:0] tap];
    
    //Checks that the concern
    XCTAssert(tablesQuery.staticTexts[@"Pending"].exists);
    XCTAssert(!tablesQuery.staticTexts[@"Seen"].exists);
    XCTAssert(!tablesQuery.staticTexts[@"Responding in 24 hours"].exists);
    XCTAssert(!tablesQuery.staticTexts[@"Responding in 48 hours"].exists);
    XCTAssert(!tablesQuery.staticTexts[@"Responding in 72 hours"].exists);
    XCTAssert(!tablesQuery.staticTexts[@"Responding in 24 hrs"].exists);
    XCTAssert(!tablesQuery.staticTexts[@"Responding in 48 hrs"].exists);
    XCTAssert(!tablesQuery.staticTexts[@"Responding in 72 hrs"].exists);
    XCTAssert(!tablesQuery.staticTexts[@"Resolved"].exists);
    XCTAssert(!tablesQuery.staticTexts[@"Retracted"].exists);
}

@end
