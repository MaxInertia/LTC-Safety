//
//  LTCValueSelectionViewModelTests.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-03.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "LTCValueSelectionViewModel.h"
#import "LTCRowValue.h"

@interface LTCValueSelectionViewModelTests : XCTestCase

@end

@implementation LTCValueSelectionViewModelTests

- (void)setUp {
    [super setUp];
}

- (void)tearDown {
    [super tearDown];
}

- (void)testViewModelForFileName {
    
    NSString *fileName = @"Categories";
    LTCValueSelectionViewModel *viewModel = [LTCValueSelectionViewModel selectionViewModelForFileName:fileName];
    
    NSString *path = [[NSBundle mainBundle] pathForResource:fileName ofType:@"plist"];
    NSDictionary *configDictionary = [NSDictionary dictionaryWithContentsOfFile:path];

    // Check that the title and prompt messages were properly set
    XCTAssertEqual(NSLocalizedString(configDictionary[@"title"], nil), viewModel.title);
    XCTAssertEqual(NSLocalizedString([configDictionary valueForKey:@"other_prompt"], nil), viewModel.otherPrompt);

    // Check that there is a row value for each item in the dictionary
    int i = 0;
    NSArray *rowDictionaries = configDictionary[@"items"];
    for (LTCRowValue *rowValue in viewModel.rowValues) {
        
        NSString *title = rowDictionaries[i][@"title"];
        XCTAssertTrue([title isEqualToString:rowValue.tag]);
        i++;
    }
}

@end
