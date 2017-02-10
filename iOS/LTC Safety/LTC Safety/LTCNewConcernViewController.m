//
//  LTCNewConcernViewController.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-26.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCNewConcernViewController.h"

NSString *const LTCNewConcernTitle = @"NEW_CONCERN_TITLE";
NSString *const LTCNewConcernError = @"NEW_CONCERN_ERROR_TITLE";

@interface LTCNewConcernViewController ()
@property (readonly, nonatomic, weak) LTCNewConcernViewModel *viewModel;
@end

@implementation LTCNewConcernViewController
@dynamic viewModel;

/**
 A getter for the view model that casts the form to the LTCNewConcernViewModel class to avoid maintaining multiple references to the same object.

 @pre self.form is of class LTCNewConcernViewModel
 @return The view model that the view controller uses to determine which input fields to show.
 */
- (LTCNewConcernViewModel *)viewModel {
    NSAssert([self.form isKindOfClass:[LTCNewConcernViewModel class]], @"Unexpected type for %@ form.", self.class);
    return (LTCNewConcernViewModel *)self.form;
}

- (instancetype)initWithViewModel:(LTCNewConcernViewModel *)viewModel {

    NSAssert(viewModel, @"Attempted to create a new concern view controller with a nil view model.");
    
    if (self = [super initWithForm:viewModel]) {
        
        self.title = NSLocalizedString(LTCNewConcernTitle, nil);
        self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemCancel target:self action:@selector(cancel)];
        
        // The submission callback when the submit button is tapped
        self.viewModel.submissionCallback = @selector(submit);
    }

    NSAssert(self.viewModel, @"%@ initializer completed with a nil view model.", self.class);
    NSAssert1(self != nil, @"Failed to initialize %@", self.class);
    
    return self;
}

/**
 The submission callback that is called when the view controller's submit button is clicked by the user.
 
 @attention Concern submission involves network communication. As a result, the post conditions will only hold after an indeterminate amount of time.
 @pre self.delegate is non-null to ensure that the delegate can be notified if a concern is successfully submitted.
 @post The delegate is notified that a concern was submitted to the client API on the backend. If the submission fails an alert controller is presented to display the error message to the user.
 */
- (void)submit {
    
    NSAssert(self.delegate != nil, @"Attempted to submit a concern with no delegate to receive the message.");
    NSAssert([self.delegate conformsToProtocol:@protocol(LTCNewConcernViewControllerDelegate)], @"The %@ delegate does not conform to the delegate's protocal.", self.class);

    // Tell the view model to submit that data that it is modeling to the server-side client API
    [self.viewModel submitConcernData:^(LTCConcern *concern, NSError *error){
        
        if (error == nil) {
            
            // Notify the delegate that a concern was successfully submitted
            [self.delegate viewController:self didSubmitConcern:concern];
            [self dismissViewControllerAnimated:YES completion:nil];
            
        } else {
            
            // Display the user error message from the client API
            NSString *errorMessage = [error.userInfo valueForKey:@"error"];
            UIAlertController *alert = [UIAlertController alertControllerWithTitle:NSLocalizedString(LTCNewConcernError, nil) message:errorMessage preferredStyle:UIAlertControllerStyleAlert];
            UIAlertAction *cancelAction = [UIAlertAction actionWithTitle:NSLocalizedString(@"OK", nil) style:UIAlertActionStyleCancel handler:nil];
            [alert addAction:cancelAction];
            
            [self presentViewController:alert animated:YES completion:nil];
        }
    }];
}

/**
 The cancel callback when the user dismisses the new concern view controller without submitting a concern.
 
 @post The new concern view controller has been dismissed.
 */
- (void)cancel {
    
    NSAssert1(self.isViewLoaded, @"Attempted to dismiss a %@ that wasn't presented", self.class);
    NSAssert1(self.view.window != nil, @"Attempted to dismiss a %@ without a window", self.class);

    [self dismissViewControllerAnimated:YES completion:nil];
}

@end
