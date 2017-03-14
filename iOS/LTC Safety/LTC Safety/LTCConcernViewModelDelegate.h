//
//  LTCConcernViewModelDelegate.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-03.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <Foundation/Foundation.h>

@class LTCConcernViewModel;

/**
 The LTCConcernViewModelDelegate protocol is by the LTCConcernViewModel class to notify its delegate when the model contents changes. This involves delegate callbacks on insertion, deletion, updating concerns, and moving to different index paths wthin the model.
 */
@protocol LTCConcernViewModelDelegate <NSObject>
@required

/**
 Required delegate method that notifies the delegate whenever one or more concerns are inserted into the concern view model.

 @param viewModel The view model that the concerns were inserted into.
 @param indexPaths The index paths that the concerns were inserted at.
 */
- (void)viewModel:(LTCConcernViewModel *)viewModel didInsertConcernsAtIndexPaths:(NSArray *)indexPaths;

/**
 Required delegate method that notifies the delegate whenever one or more concern is deleted from the concern view model.

 @param viewModel The view model that the concerns were deleted from.
 @param indexPaths The index paths that the concerns were deleted from.
 */
- (void)viewModel:(LTCConcernViewModel *)viewModel didDeleteConcernsAtIndexPaths:(NSArray *)indexPaths;

/**
 Required delegate method that notifies the delegate whenever a concern's contents is updated.
 
 @param viewModel The view model that the concern belongs to.
 @param indexPath The index path that the concern was updated at.
 */
- (void)viewModel:(LTCConcernViewModel *)viewModel didUpdateConcern:(LTCConcern *)concern atIndexPath:(NSIndexPath *)indexPath;

/**
 Required delegate method that notifies the delegate whenever a concern changes index paths within the view model.

 @param viewModel The view model that the concern belongs to.
 @param fromIndexPath The index path that the concern started at.
 @param toIndexPath The index path that the concern moved to.
 */
- (void)viewModel:(LTCConcernViewModel *)viewModel didMoveConcernFromIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath;

/**
 Required delegate method that notifies the delegate when the view model starts to update. This may be for insertion, deletion, moving, or updating a concern.

 @param viewModel The view model that is being updated.
 */
- (void)viewModelWillBeginUpdates:(LTCConcernViewModel *)viewModel;

/**
 Required delegate method that notifies the delegate when the view model finishes updating. This may be for insertion, deletion, moving, or updating a concern.
 
 @param viewModel The view model that finished being updated.
 */
- (void)viewModelDidFinishUpdates:(LTCConcernViewModel *)viewModel;
@end
