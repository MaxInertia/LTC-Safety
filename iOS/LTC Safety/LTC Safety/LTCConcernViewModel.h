//
//  LTCConcernViewModel.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-27.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "LTCConcern+CoreDataClass.h"
#import "LTCConcernViewModelDelegate.h"
#import "LTCClientApi.h"


extern NSString * const LTCUpdatedConcernStatusNotification;

/**
 The LTCConcernViewModel class is used for modelling the data required for the LTCConcernViewController to display the client's submitted concerns.
 */
@interface LTCConcernViewModel : NSObject

/**
 The completion for a refresh concern call. Contains a possible error message that could be brought from the clientApi call.
 */
typedef void(^LTCRefreshConcernsCompletion)(NSError *error);

/**
 The clientApi for this view model used for making calls to the datastore.
 */
@property (nonatomic, strong) LTCClientApi *clientApi;

/**
 The number of sections in the view model which is guaranteed to be non-negative.
 */
@property (readonly, nonatomic, assign) NSUInteger sectionCount;

/**
 The managed object context that concerns are inserted to and deleted from.
 */
@property (readonly, strong, nonatomic) NSManagedObjectContext *objectContext;

/**
 The delegate that is notified when the view model updates. This could be insertion, deletion, updating properties of concerns, or re-arranging concerns.
 */
@property (nonatomic, weak) id <LTCConcernViewModelDelegate>delegate;

/**
 Designated initializer that initializes a concern view model with a managed object context.

 @pre The context != nil
 @pre The context is loaded using a persistent store coordinator for the LTC_Safety data model.
 @param context The managed object context used to load and save concerns.
 @return An LTCConcernViewModel object initialized with a non-nil managed object context.
 */
- (instancetype)initWithContext:(NSManagedObjectContext *)context;


/**
 Inserts a new concern into the view model.

 @pre The concern is non-nil and has not been inserted into the view model
 @param concern The concern to be inserted.
 @param error An error that is non-nil if saving the concern failed.
 @post The concern has been saved to disk.
 */
- (void)addConcern:(LTCConcern *)concern error:(NSError **)error;

/**
 Removes an existing concern from the view model.

 @pre The concern is currently part of the view model.
 @param concern The concern to be removed.
 @param error An error that is non-nil if an error occurred saving the managed object context after removal.
 @post The concern has been removed from the view model.
 */
- (void)removeConcern:(LTCConcern *)concern error:(NSError **)error;

/**
 Fetches the concern at the specified index path within the view model.

 @pre indexPath.section is between 0 and the self.sectionCount
 @pre indexPath.row is less than the number of rows in the section and greater than or equal to 0
 @param indexPath The index path of the concern.
 @return The concern that was located at the index path.
 */
- (LTCConcern *)concernAtIndexPath:(NSIndexPath *)indexPath;

/**
 Returns the number of rows in the specificed section.

 @pre indexPath.section is between 0 and the self.sectionCount
 @param section The section number.
 @return The number of rows in the section.
 */
- (NSUInteger )rowCountForSection:(NSUInteger)section;
/**
 Fetches the list of concerns from the datastore and saves them to the viewModel using the list of owner tokens in the owner token list wrapper.
 
 @pre The view model's object context is non-nil and assigned correctly.
 @param tokensWrapper the wrapper of a list of owner tokens to be fetched from the datastore.
 @param completion the completion for this method that containes a possible error message to be brought back from the clientApi call.
 */
- (void)refreshConcernsWithCompletion:(GTLRClient_OwnerTokenListWrapper *)tokensWrapper completion:(LTCRefreshConcernsCompletion)completion;
@end
