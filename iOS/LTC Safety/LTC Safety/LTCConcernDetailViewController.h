//
//  LTCConcernDetailViewController.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-26.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "LTCConcern+CoreDataClass.h"
#import "LTCConcernDetailViewModel.h"
#import <XLForm/XLForm.h>

/**
 The LTCConcernDetailViewController class is used for displaying to the user the data associated with a single submitted concern in the LTCConcernViewController. It stores the static data that resulted from a user submission.
 */
@interface LTCConcernDetailViewController : XLFormViewController

/**
 The view model that models that concern data displayed by the detail view controller.
 */
@property (readonly, nonatomic, strong) LTCConcernDetailViewModel *viewModel;

/**
 Designated initializer for a concern detail view controller displays the information stored in the view model to the user.

 @pre viewModel != nil
 @param viewModel The view model containing the data to be represented by the view controller.
 @return An LTCConcernDetailViewController object that has been configured for the view model.
 @post The view model information is displayed within the view controller's view.
 */

/**
 The concern who's information is being displayed in the detail view controller.
 */
@property (strong, nonatomic) LTCConcern *concern;

- (instancetype)initWithViewModel:(LTCConcernDetailViewModel *)viewModel;
@end

