//
//  LTCConcernViewModel.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-27.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCConcernViewModel.h"

@interface LTCConcernViewModel () <NSFetchedResultsControllerDelegate>
@property (strong, nonatomic) NSFetchedResultsController<LTCConcern *> *fetchedResultsController;
@property (strong, nonatomic) NSManagedObjectContext *managedObjectContext;
@end

@implementation LTCConcernViewModel
@dynamic sectionCount;

- (NSUInteger)sectionCount {
    return 0;
}

- (NSUInteger )rowCountForSection:(NSUInteger)section {
    return 0;
}

- (instancetype)initWithContext:(NSManagedObjectContext *)context {
    return nil;
}

- (void)addConcern:(LTCConcern *)concern {

}

- (LTCConcern *)concernAtIndexPath:(NSIndexPath *)indexPath {
    return nil;
}

- (void)removeConcern:(LTCConcern *)concern {
    
}

@end
