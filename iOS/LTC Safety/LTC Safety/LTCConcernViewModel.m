//
//  LTCConcernViewModel.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-27.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCConcernViewModel.h"
#import "Logger.h"
@interface LTCConcernViewModel () <NSFetchedResultsControllerDelegate>
@property (readwrite, strong, nonatomic) NSFetchedResultsController *fetchedResultsController;
@property (readwrite, strong, nonatomic) NSManagedObjectContext *objectContext;
@end

@implementation LTCConcernViewModel
@dynamic sectionCount;

/**
 Constructor method for the concern view model. Initialized a new view model with a new
 NSFetchedResultsController and a passed in NSManagedObjectContext
 
 @param context The NSManagedObjectContext that objects can be saved into.

 @return The LTCConcernViewModel just created
 */
- (instancetype)initWithContext:(NSManagedObjectContext *)context {
    
    NSAssert(context != nil, @"Attempted to initialize the concern view model with a nil context.");
    
    if (self = [super init]) {
        self.fetchedResultsController = [self _loadFetchedResultsControllerForContext:context];
        
        NSAssert(self.fetchedResultsController != nil, @"Failed to load fetched results controller.");

        self.fetchedResultsController.delegate = self;
        self.objectContext = context;
    }
    return self;
}

#pragma mark - Fetched results controller

/**
 Loads a fetch result controller for the current managed object context for fetching concerns from the sqlite file.

 @todo Error handling needs to be fixed for this method in case loading the fetched results controller fails.
 @pre context != nil
 @param context The context that the fetch result controller uses to fetch its data.
 @return A non-nil fetched results controller that can be used for fetching concerns from disk.
 */
- (NSFetchedResultsController *)_loadFetchedResultsControllerForContext:(NSManagedObjectContext *)context {
    
    NSAssert(context != nil, @"Attempted to load a fetched results controller with a nil context.");
    
    NSFetchRequest *fetchRequest = [LTCConcern fetchRequest];
    [fetchRequest setFetchBatchSize:20];
    
    // Sort based on submission date
    NSSortDescriptor *sortDescriptor = [[NSSortDescriptor alloc] initWithKey:@"submissionDate" ascending:NO];
    fetchRequest.sortDescriptors = @[sortDescriptor];
    
    NSFetchedResultsController *fetchedResultsController = [[NSFetchedResultsController alloc] initWithFetchRequest:fetchRequest managedObjectContext:context sectionNameKeyPath:nil cacheName:@"Master"];
    fetchedResultsController.delegate = self;
    
    NSError *error = nil;
    if (![fetchedResultsController performFetch:&error]) {
        // TODO properly handle error
        NSLog(@"Unresolved error %@, %@", error, error.userInfo);
        abort();
    }
    return fetchedResultsController;
}

- (NSUInteger)sectionCount {
    
    NSAssert(self.fetchedResultsController.sections.count > 0, @"Invalid number of sections for fetched results controller.");
    
    return self.fetchedResultsController.sections.count;
}

- (NSUInteger )rowCountForSection:(NSUInteger)section {
    
    NSAssert1(section >= 0 && section < self.fetchedResultsController.sections.count, @"Section out of bounds for row count: %lu", (unsigned long)section);
    
    id <NSFetchedResultsSectionInfo> sectionInfo = [self.fetchedResultsController sections][section];
    return [sectionInfo numberOfObjects];
}

/**
 Adds a concern to the core data local datastore.

 @param concern The concern to be stored
 @param error   <#error description#>
 */
- (void)addConcern:(LTCConcern *)concern error:(NSError **)error {
    [Logger log :@"Adding a concern" level:kLTCLogLevelInfo];
    NSAssert(concern != nil, @"Attempted to add a nil concern.");
    NSAssert(error != nil, @"Attempted to call add concern without an error handler.");
    
    [self.objectContext save:error];
    [Logger log:@"Concern added" level:kLTCLogLevelInfo];
}

- (LTCConcern *)concernAtIndexPath:(NSIndexPath *)indexPath {
    
    NSAssert1(indexPath.section >= 0 && indexPath.section < self.fetchedResultsController.sections.count, @"Attempted to get concern in out of bounds section: %lu", (unsigned long)indexPath.section);
    
    return [self.fetchedResultsController objectAtIndexPath:indexPath];
}

- (void)removeConcern:(LTCConcern *)concern error:(NSError **)error {
    [Logger log:@"Removing a concern" level:kLTCLogLevelInfo];
    NSAssert(concern != nil, @"Attempted to remove a nil concern.");
    NSAssert(error != nil, @"Attempted to call remove concern without an error handler.");
    NSAssert([concern.managedObjectContext isEqual:self.objectContext], @"Attempted to remove a concern that was not a part of the managed object context.");
    
    NSManagedObjectContext *context = [self.fetchedResultsController managedObjectContext];
    [context deleteObject:concern];
    
    [self.objectContext save:error];
    [Logger log :@"Concern removed" level:kLTCLogLevelInfo];
}

#pragma mark - NSFetchedResultsController delegate

- (void)controllerWillChangeContent:(NSFetchedResultsController *)controller {
    
    NSAssert(self.delegate != nil, @"Attempted to begin updates on nil delegate.");

    [self.delegate viewModelWillBeginUpdates:self];
}

- (void)controllerDidChangeContent:(NSFetchedResultsController *)controller {
    
    NSAssert(self.delegate != nil, @"Attempted to finish updates on nil delegate.");

    [self.delegate viewModelDidFinishUpdates:self];
}

/**
 Notifies the delegate whenever the the fetched results controller is updated.
 */
- (void)controller:(NSFetchedResultsController *)controller didChangeObject:(id)anObject
       atIndexPath:(NSIndexPath *)indexPath forChangeType:(NSFetchedResultsChangeType)type
      newIndexPath:(NSIndexPath *)newIndexPath {

    NSAssert(self.delegate != nil, @"Attempted to notify a nil delegate of a changed object.");
    
    switch(type) {
        case NSFetchedResultsChangeInsert:
            [self.delegate viewModel:self didInsertConcernsAtIndexPaths:@[newIndexPath]];
            break;
            
        case NSFetchedResultsChangeDelete:
            [self.delegate viewModel:self didDeleteConcernsAtIndexPaths:@[indexPath]];
            break;
            
        case NSFetchedResultsChangeUpdate:
            NSAssert1([anObject isKindOfClass:[LTCConcern class]], @"Unexpected object did change in results controller: %@", anObject);
            [self.delegate viewModel:self didUpdateConcern:anObject atIndexPath:indexPath];
            break;
            
        case NSFetchedResultsChangeMove:
            [self.delegate viewModel:self didMoveConcernFromIndexPath:indexPath toIndexPath:newIndexPath];
            break;
    }
}

@end
