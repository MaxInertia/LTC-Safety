//
//  LTCValueSelectionViewModel.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-03.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCValueSelectionViewModel.h"

// The keys within the configuration plist for the three required values.
NSString *const LTCModelTitleKey = @"title";
NSString *const LTCOtherPromptKey = @"other_prompt";
NSString *const LTCItemsKey = @"items";

@interface LTCValueSelectionViewModel ()
@property (readwrite, nonatomic, strong) NSString *title;
@property (readwrite, nonatomic, strong) NSString *otherPrompt;
@property (readwrite, nonatomic, strong) NSArray<LTCRowValue *> *rowValues;
@end

@implementation LTCValueSelectionViewModel

+ (instancetype)selectionViewModelForFileName:(NSString *)fileName {
    
    NSAssert(fileName.length > 0, @"Attempted to create a selection from model with an empty file name.");

    // Load the path at the file name
    NSString *path = [[NSBundle mainBundle] pathForResource:fileName ofType:@"plist"];
    NSAssert1(path != nil, @"Failed to find selection view model with file name: %@", fileName);
    
    // Load the configuration dictionary at the file path
    NSDictionary *configDictionary = [NSDictionary dictionaryWithContentsOfFile:path];
    NSAssert1(configDictionary != nil, @"Failed to load dictionary at path: %@", path);
    
    // Get the title and other prompt descriptors
    NSString *titleDescriptor = [configDictionary valueForKey:LTCModelTitleKey];
    NSString *otherPromptDescriptor = [configDictionary valueForKey:LTCOtherPromptKey];
    NSAssert1(titleDescriptor != nil, @"Failed to find title descriptor in: %@", configDictionary);
    NSAssert1(otherPromptDescriptor != nil, @"Failed to find other prompt descriptor in: %@", configDictionary);

    // Get the pre-defined row values in the configuration
    NSArray *rowDictionaries = [configDictionary valueForKey:LTCItemsKey];
    NSAssert1(rowDictionaries.count > 0, @"Failed to find row items in: %@", configDictionary);
    
    NSMutableArray *mutableRowValues = [NSMutableArray arrayWithCapacity:rowDictionaries.count];
    
    // Construct an LTCRowValue object for each row dictionary
    for (NSDictionary *dictionary in rowDictionaries) {
        LTCRowValue *rowValue = [[LTCRowValue alloc] initWithDictionary:dictionary];
        [mutableRowValues addObject:rowValue];
    }
    NSArray *rowValues = [mutableRowValues copy];
    NSAssert(rowValues.count == rowDictionaries.count, @"Unexpected number of row values created.");
    
    return [[self alloc] initWithTitleDescriptor:titleDescriptor otherPromptDescriptor:otherPromptDescriptor rowValues:rowValues];
}

/**
 The designated initializor to create a LTCValueSelectionViewModel object based on values loaded from a configuration file. This should not be called directly, use +selectionViewModelForFileName: instead.

 @pre The title descriptor maps to an existing string in Localizable.strings
 @pre The other prompt descriptor maps to an existing string in Localizable.strings
 @pre rowValues.count > 0 because there must be at least one predefined option.
 @param titleDescriptor The descriptor that maps to the localized title value in Localizable.strings
 @param otherPromptDescriptor The descriptor that maps to the localized other pormpt value in Localizable.strings
 @param rowValues The list of pre-defined row values to be displayed in the value selection controller
 @return An LTCValueSelectionViewModel object initialized with a title, other prompt title, and pre-defined row values.
 */
- (instancetype)initWithTitleDescriptor:(NSString *)titleDescriptor otherPromptDescriptor:(NSString *)otherPromptDescriptor rowValues:(NSArray <LTCRowValue *>*)rowValues {
    
    NSAssert(NSLocalizedString(titleDescriptor, nil) != nil, @"Attempted to initialize a value selection view model with a nil title.");
    NSAssert(NSLocalizedString(otherPromptDescriptor, nil) != nil, @"Attempted to initialize a value selection view model with a nil other prompt.");
    NSAssert(rowValues.count > 0, @"Attempted to initialize a value selection view model with zero row values.");

    if (self = [super init]) {
        
        self.title = NSLocalizedString(titleDescriptor, nil);
        self.otherPrompt = NSLocalizedString(otherPromptDescriptor, nil);
        self.rowValues = rowValues;
    }
    NSAssert1(self != nil, @"Failed to initialize %@", self.class);
    NSAssert(self.title != nil, @"Value selection view model finished initialization with a nil title.");
    NSAssert(self.otherPrompt != nil, @"Value selection view model finished initialization with a nil other prompt.");
    NSAssert(self.rowValues.count > 0, @"Value selection view model finished initialization with no row values.");

    return self;
}

@end
