//
//  LTCConcernDetailViewModel.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-04.
//  Modified by Daniel Morris
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <XLForm/XLForm.h>
#import "LTCConcern+CoreDataClass.h"

/**
 The LTCConcernDetailViewModel is responsible for modeling LTCConcern data in a way so that the LTCConcernDetailViewController can display the concern's data to the user. This involves setting up sections and rows to display the concern's properties.
 
 History properties: The concern in this class is subject to change.
 
 Invariance properties: This class assumes that the given viewModel is valid and non-nil and that the current concern is also valid and non-nil.
 
 */
@interface LTCConcernDetailViewModel : XLFormDescriptor

/**
 The concern that the detail view model uses to populate its row data.
 */
@property (readonly, nonatomic, strong) LTCConcern *concern;

/**
 The callback that is performed when the retract concern button is clicked.
 */
@property (nonatomic, assign) SEL retractCallback;

/**
 Designated initializer to construct a new detail view model.

 @pre concern != nil
 @param concern The concern who's data is used to populate the form model.
 @return An LTCConcernDetailViewModel object configured to have rows for the concern's properties.
 */
- (instancetype)initWithConcern:(LTCConcern *)concern;
@end
