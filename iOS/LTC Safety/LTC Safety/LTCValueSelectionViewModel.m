//
//  LTCValueSelectionViewModel.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-03.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCValueSelectionViewModel.h"

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
    
    NSString *path = [[NSBundle mainBundle] pathForResource:fileName ofType:@"plist"];
    
    NSAssert1(path != nil, @"Failed to find selection view model with file name: %@", fileName);
    
    NSDictionary *configDictionary = [NSDictionary dictionaryWithContentsOfFile:path];
    
    NSAssert1(path != nil, @"Failed to load dictionary at path: %@", path);
    
    NSString *titleDescriptor = [configDictionary valueForKey:LTCModelTitleKey];
    NSString *otherPromptDescriptor = [configDictionary valueForKey:LTCOtherPromptKey];
    
    NSAssert1(titleDescriptor != nil, @"Failed to find title descriptor in: %@", configDictionary);
    NSAssert1(otherPromptDescriptor != nil, @"Failed to find other prompt descriptor in: %@", configDictionary);

    NSArray *rowDictionaries = [configDictionary valueForKey:LTCItemsKey];
    
    NSAssert1(rowDictionaries.count > 0, @"Failed to find row items in: %@", configDictionary);
    
    NSMutableArray *mutableRowValues = [NSMutableArray arrayWithCapacity:rowDictionaries.count];
    
    for (NSDictionary *dictionary in rowDictionaries) {
        LTCRowValue *rowValue = [[LTCRowValue alloc] initWithDictionary:dictionary];
        [mutableRowValues addObject:rowValue];
    }
    NSArray *rowValues = [mutableRowValues copy];
    
    NSAssert(rowValues.count == rowDictionaries.count, @"Unexpected number of row values created.");
    
    return [[self alloc] initWithTitleDescriptor:titleDescriptor otherPromptDescriptor:otherPromptDescriptor rowValues:rowValues];
}

- (instancetype)initWithTitleDescriptor:(NSString *)titleDescriptor otherPromptDescriptor:(NSString *)otherPromptDescriptor rowValues:(NSArray <LTCRowValue *>*)rowValues {
    
    NSAssert(titleDescriptor != nil, @"Attempted to initialize a value selection view model with a nil title descriptor.");
    NSAssert(otherPromptDescriptor != nil, @"Attempted to initialize a value selection view model with a nil other prompt descriptor.");
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
