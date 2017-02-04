//
//  LTCConcernViewModelDelegate.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-03.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <Foundation/Foundation.h>

@class LTCConcernViewModel;
@protocol LTCConcernViewModelDelegate <NSObject>
@required
- (void)viewModel:(LTCConcernViewModel *)viewModel didInsertConcernsAtIndexPaths:(NSArray *)indexPaths;
- (void)viewModel:(LTCConcernViewModel *)viewModel didDeleteConcernsAtIndexPaths:(NSArray *)indexPaths;
- (void)viewModel:(LTCConcernViewModel *)viewModel didUpdateConcern:(LTCConcern *)concern atIndexPath:(NSIndexPath *)indexPath;
- (void)viewModel:(LTCConcernViewModel *)viewModel didMoveConcernFromIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath;
- (void)viewModelWillBeginUpdates:(LTCConcernViewModel *)viewModel;
- (void)viewModelDidFinishUpdates:(LTCConcernViewModel *)viewModel;
@end
