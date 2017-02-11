//
//  LTC_SafetyUITests.m
//  LTC SafetyUITests
//
//  Created by Allan Kerr on 2017-01-26.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XCTest/XCTest.h>

@interface LTC_SafetyUITests : XCTestCase

@end

@implementation LTC_SafetyUITests

- (void)setUp {
    [super setUp];
    
    [[[XCUIApplication alloc] init] launch];
}

- (void)tearDown {
    // Put teardown code here. This method is called after the invocation of each test method in the class.
    [super tearDown];
    [[[XCUIApplication alloc] init] terminate];
}

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
    [tablesQuery.staticTexts[@"Category Five"] tap];
    [tablesQuery.staticTexts[@"Facility"] tap];
    [tablesQuery.staticTexts[@"Preston Extendicare"] tap];
    [tablesQuery.textFields[@"Room Number"] tap];
    [moreNumbersKey tap];
    XCUIElement *roomTextField = tablesQuery.textFields[@"Room Number"];
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

- (void)testNullEmailPhone {
    
    
    XCUIApplication *app = [[XCUIApplication alloc] init];
    [app.buttons[@"New Safety Concern"] tap];
    
    XCUIElementQuery *tablesQuery = app.tables;
    XCUIElement *nameTextField = tablesQuery.textFields[@"First and Last Name"];
    [nameTextField tap];
    
    [nameTextField typeText:@"Test Tan"];
    
    
    XCUIElement *moreNumbersKey = app.keys[@"more, numbers"];
    
    [tablesQuery.staticTexts[@"Nature of Concern"] tap];
    [tablesQuery.staticTexts[@"Category Five"] tap];
    // Failed to find matching element please file bug (bugreport.apple.com) and provide output from Console.app
    [tablesQuery.staticTexts[@"Facility"] tap];
    [tablesQuery.staticTexts[@"Preston Extendicare"] tap];
    [tablesQuery.textFields[@"Room Number"] tap];
    [moreNumbersKey tap];
    XCUIElement *roomTextField = tablesQuery.textFields[@"Room Number"];
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
    [tablesQuery.staticTexts[@"Category Five"] tap];
    [tablesQuery.staticTexts[@"Facility"] tap];
    [tablesQuery.staticTexts[@"Preston Extendicare"] tap];
    [tablesQuery.textFields[@"Room Number"] tap];
    [moreNumbersKey tap];
    XCUIElement *roomTextField = tablesQuery.textFields[@"Room Number"];
    [roomTextField typeText:@"45"];
    
    XCUIElement *textView = [[[tablesQuery childrenMatchingType:XCUIElementTypeCell] elementBoundByIndex:6] childrenMatchingType:XCUIElementTypeTextView].element;
    [textView tap];
    [textView typeText:@"None"];
    //[[[[[app childrenMatchingType:XCUIElementTypeWindow] elementBoundByIndex:0] childrenMatchingType:XCUIElementTypeOther].element childrenMatchingType:XCUIElementTypeOther].element tap];
    [tablesQuery.staticTexts[@"Submit Concern"] tap];
    
    
    // Use recording to get started writing UI tests.
    // Use XCTAssert and related functions to verify your tests produce the correct results.
    
    XCTAssert(app.alerts.element.staticTexts[@"A first and last name must be provided when submitting a concern." ].exists);
}

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
    [tablesQuery.staticTexts[@"Category Five"] tap];
    // Failed to find matching element please file bug (bugreport.apple.com) and provide output from Console.app
    [tablesQuery.staticTexts[@"Facility"] tap];
    [tablesQuery.staticTexts[@"Preston Extendicare"] tap];
    [tablesQuery.textFields[@"Room Number"] tap];
    [moreNumbersKey tap];
    XCUIElement *roomTextField = tablesQuery.textFields[@"Room Number"];
    [roomTextField typeText:@"45"];
    
    XCUIElement *textView = [[[tablesQuery childrenMatchingType:XCUIElementTypeCell] elementBoundByIndex:6] childrenMatchingType:XCUIElementTypeTextView].element;
    [textView tap];
    [textView typeText:@"None"];
    //[[[[[app childrenMatchingType:XCUIElementTypeWindow] elementBoundByIndex:0] childrenMatchingType:XCUIElementTypeOther].element childrenMatchingType:XCUIElementTypeOther].element tap];
    [tablesQuery.staticTexts[@"Submit Concern"] tap];
    
    
    // Use recording to get started writing UI tests.
    // Use XCTAssert and related functions to verify your tests produce the correct results.
}

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
    [tablesQuery.staticTexts[@"Category Five"] tap];
    // Failed to find matching element please file bug (bugreport.apple.com) and provide output from Console.app
    [tablesQuery.staticTexts[@"Facility"] tap];
    [tablesQuery.staticTexts[@"Preston Extendicare"] tap];
    [tablesQuery.textFields[@"Room Number"] tap];
    [moreNumbersKey tap];
    XCUIElement *roomTextField = tablesQuery.textFields[@"Room Number"];
    [roomTextField typeText:@"45"];
    
    XCUIElement *textView = [[[tablesQuery childrenMatchingType:XCUIElementTypeCell] elementBoundByIndex:6] childrenMatchingType:XCUIElementTypeTextView].element;
    [textView tap];
    [textView typeText:@"None"];
    //[[[[[app childrenMatchingType:XCUIElementTypeWindow] elementBoundByIndex:0] childrenMatchingType:XCUIElementTypeOther].element childrenMatchingType:XCUIElementTypeOther].element tap];
    [tablesQuery.staticTexts[@"Submit Concern"] tap];
    
    
    // Use recording to get started writing UI tests.
    // Use XCTAssert and related functions to verify your tests produce the correct results.
}

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
    [tablesQuery.textFields[@"Room Number"] tap];
    [moreNumbersKey tap];
    XCUIElement *roomTextField = tablesQuery.textFields[@"Room Number"];
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
    [tablesQuery.staticTexts[@"Category Five"] tap];
    // Failed to find matching element please file bug (bugreport.apple.com) and provide output from Console.app
    [tablesQuery.textFields[@"Room Number"] tap];
    [moreNumbersKey tap];
    XCUIElement *roomTextField = tablesQuery.textFields[@"Room Number"];
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
    [tablesQuery.staticTexts[@"Category Five"] tap];
    // Failed to find matching element please file bug (bugreport.apple.com) and provide output from Console.app
    [tablesQuery.staticTexts[@"Facility"] tap];
    [tablesQuery.staticTexts[@"Preston Extendicare"] tap];
    [tablesQuery.textFields[@"Room Number"] tap];
    [moreNumbersKey tap];
    
    XCUIElement *textView = [[[tablesQuery childrenMatchingType:XCUIElementTypeCell] elementBoundByIndex:6] childrenMatchingType:XCUIElementTypeTextView].element;
    [textView tap];
    [textView typeText:@"None"];
    //[[[[[app childrenMatchingType:XCUIElementTypeWindow] elementBoundByIndex:0] childrenMatchingType:XCUIElementTypeOther].element childrenMatchingType:XCUIElementTypeOther].element tap];
    [tablesQuery.staticTexts[@"Submit Concern"] tap];
    
    
    // Use recording to get started writing UI tests.
    // Use XCTAssert and related functions to verify your tests produce the correct results.
}

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
    [tablesQuery.staticTexts[@"Category Five"] tap];
    // Failed to find matching element please file bug (bugreport.apple.com) and provide output from Console.app
    [tablesQuery.staticTexts[@"Facility"] tap];
    [tablesQuery.staticTexts[@"Preston Extendicare"] tap];
    [tablesQuery.textFields[@"Room Number"] tap];
    [moreNumbersKey tap];
    XCUIElement *roomTextField = tablesQuery.textFields[@"Room Number"];
    [roomTextField typeText:@"45"];
    
    //[[[[[app childrenMatchingType:XCUIElementTypeWindow] elementBoundByIndex:0] childrenMatchingType:XCUIElementTypeOther].element childrenMatchingType:XCUIElementTypeOther].element tap];
    [tablesQuery.staticTexts[@"Submit Concern"] tap];
    
    
    // Use recording to get started writing UI tests.
    // Use XCTAssert and related functions to verify your tests produce the correct results.
}


@end
