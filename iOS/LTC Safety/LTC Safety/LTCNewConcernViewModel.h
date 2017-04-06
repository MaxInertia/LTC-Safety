//
//  LTCNewConcernViewModel.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-26.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XLForm/XLForm.h>
#import "LTCConcern+CoreDataClass.h"

/**
 The LTCNewConcernViewModel class is used to model the concern submission form that is presented to the controller. This class is used by the LTCNewConcernViewController to determine which input fields should be displayed to the user and is updated whenever these input fields are modified.
 
 History properties: Instances of this class should not vary from the time they are created.
 
 Invariance properties: This class makes no assumptions.
 
 */
@interface LTCNewConcernViewModel : XLFormDescriptor

/**
 The submission callback that is called on the LTCNewConcernViewController when the submit button is tapped.
 */
@property (nonatomic, assign) SEL submissionCallback;

/**
 Designated initializer for creating an LTCNewConcernViewModel object with a managed object context for inserting newly submitted concerns into.

 @note Although any submitted concerns are not saved immediately upon creation, CoreData requires that entities are initialized with a managed object context. As a result, a the application-wide context must be passed strictly for entity initialization.
 @pre concern != nil
 @param context The managed object context that submitted concerns are added to.
 @return An LTCNewConcernViewModel object that models the concern input form displayed to the user.
 */
- (instancetype)initWithContext:(NSManagedObjectContext *)context;

/**
 Submits the concern data currently modeled by the LTCNewConcernViewModel object to the client API on the backend server. The data that the LTCNewConcernViewModel object models is updated in real time as user input is provided.

 @attention Concern submission involves network communication meaning the completion block will not be called immediately.
 @param completion The completion block that is called when a response is received from the client API on the backend server after concern submission. If the submission failed an error will be sent back with a user readable error message; otherwise, the concern that was submitted is passed back and error is nil.
 */
- (void)submitConcernData:(void (^)(LTCConcern *concern, NSError *error))completion;
@end
