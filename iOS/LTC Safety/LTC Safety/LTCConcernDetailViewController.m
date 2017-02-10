//
//  LTCConcernDetailViewController.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-26.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCConcernDetailViewController.h"
#import "LTCClientApi.h"
#import "LTCConcernStatus+CoreDataClass.h"
#import "GTLRClient.h"

NSString *const LTCDetailConcernTitle = @"DETAIL_CONCERN_TITLE";
NSString *const LTCDetailConcernEdit = @"DETAIL_EDIT_CONCERN";


@interface LTCConcernDetailViewController ()
@property (readwrite, nonatomic, strong) LTCConcernDetailViewModel *viewModel;
@end

@implementation LTCConcernDetailViewController
@dynamic viewModel;

- (LTCConcernDetailViewModel *)viewModel {
    NSAssert([self.form isKindOfClass:[LTCConcernDetailViewModel class]], @"Unexpected type for %@ form.", self.class);
    // Cast the form to a detail view model
    return (LTCConcernDetailViewModel *)self.form;
}

- (instancetype)initWithViewModel:(LTCConcernDetailViewModel *)viewModel {
    if (self = [super initWithForm:viewModel]) {
        self.title = NSLocalizedString(@"View Concern", nil);
        // set the view model retract callback

    }
    NSAssert(self.viewModel, @"%@ initializer completed with a nil view model.", self.class);
    NSAssert1(self != nil, @"Failed to initialize %@", self.class);
    return self;
}


/**
 The callback for when the retract concern button is clicked causing a request to be sent to the backend requesting that the concern status be changed to retracted.
 */
- (void)_retractConcern {
    
    // Call the retract concern endpoint on the Client API using the view model's concern's owner token

}

@end
