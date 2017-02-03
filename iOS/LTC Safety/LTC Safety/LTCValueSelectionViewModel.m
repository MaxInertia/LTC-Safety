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
    
}

- (instancetype)initWithTitleDescriptor:(NSString *)titleDescriptor otherPromptDescriptor:(NSString *)otherPromptDescriptor rowValues:(NSArray <LTCRowValue *>*)rowValues {
    
   
}

@end
