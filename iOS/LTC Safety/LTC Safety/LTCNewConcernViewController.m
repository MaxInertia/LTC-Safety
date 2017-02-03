//
//  LTCNewConcernViewController.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-26.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCNewConcernViewController.h"

NSString *const LTCNewConcernTitle = @"NEW_CONCERN_TITLE";

@interface LTCNewConcernViewController ()
@property (readonly, nonatomic, weak) LTCNewConcernViewModel *viewModel;
- (void)submit;
- (void)cancel;
@end

@implementation LTCNewConcernViewController
@dynamic viewModel;

- (LTCNewConcernViewModel *)viewModel {
    NSAssert([self.form isKindOfClass:[LTCNewConcernViewModel class]], @"Unexpected type for %@ form.", self.class);
    return (LTCNewConcernViewModel *)self.form;
}

- (instancetype)initWithViewModel:(LTCNewConcernViewModel *)viewModel {

    NSAssert(viewModel, @"Attempted to create a new concern view controller with a nil view model.");
    
    if (self = [super initWithForm:viewModel]) {
        self.title = NSLocalizedString(LTCNewConcernTitle, nil);
        self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemCancel target:self action:@selector(cancel)];
        self.viewModel.submissionCallback = @selector(submit);
    }

    NSAssert(self.viewModel, @"%@ initializer completed with a nil view model.", self.class);
    NSAssert1(self != nil, @"Failed to initialize %@", self.class);
    
    return self;
}

- (void)submit {
    
    NSAssert(self.delegate != nil, @"Attempted to submit a concern with no delegate to receive the message.");
    NSAssert([self.delegate conformsToProtocol:@protocol(LTCNewConcernViewControllerDelegate)], @"The %@ delegate does not conform to the delegate's protocal.", self.class);

    [self.viewModel submitConcernData:^(LTCConcern *concern, NSError *error){
        if (error == nil) {
            [self.delegate viewController:self didSubmitConcern:concern];
            [self dismissViewControllerAnimated:YES completion:nil];
        } else {
            // TODO display error
        }
    }];
}

- (void)cancel {
    
    NSAssert1(self.isViewLoaded, @"Attempted to dismiss a %@ that wasn't presented", self.class);
    NSAssert1(self.view.window != nil, @"Attempted to dismiss a %@ without a window", self.class);

    [self dismissViewControllerAnimated:YES completion:nil];
}

@end
