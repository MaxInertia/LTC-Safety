//
//  LTCNewConcernViewController.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-26.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XLForm/XLForm.h>
#import "LTCNewConcernViewControllerDelegate.h"
#import "LTCNewConcernViewModel.h"

/**
 The LTCNewConcernViewController class is used to present a concern data form that the user can use to fill in the information for a concern that they have. This class is responsible for displaying the form specified in the LTCNewConcernViewModel class.
 
 History properties: Instances of this class should not vary from the time they are created.
 
 Invariance properties: This class assumes that the delegate is valid and non-nil.
 
 */
@interface LTCNewConcernViewController : XLFormViewController

/**
 The delegate that is notified when the user's concern data is sucessfully sent to the client API on the backend server.
 */
@property (nonatomic, weak) id <LTCNewConcernViewControllerDelegate>delegate;

/**
 The designated initializer for constructing an LTCNewConcernViewController object to display a concern submission form to the user.

 @pre viewModel
 @param viewModel The view model specifying which input fields should be displayed to the user.
 @return An LTCNewConcernViewController object setup to display the view model's input fields.
 */
- (instancetype)initWithViewModel:(LTCNewConcernViewModel *)viewModel;
@end
