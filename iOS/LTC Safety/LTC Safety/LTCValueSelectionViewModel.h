//
//  LTCValueSelectionViewModel.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-03.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "LTCRowValue.h"

@interface LTCValueSelectionViewModel : NSObject
@property (readonly, nonatomic, strong) NSString *title;
@property (readonly, nonatomic, strong) NSString *otherPrompt;
@property (readonly, nonatomic, strong) NSArray<LTCRowValue *> *rowValues;
+ (instancetype)selectionViewModelForFileName:(NSString *)fileName;
@end
