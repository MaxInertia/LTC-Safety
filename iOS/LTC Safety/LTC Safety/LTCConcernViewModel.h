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

@interface LTCConcernViewModel : NSObject
@property (readonly, nonatomic, assign) NSUInteger sectionCount;
@property (readonly, strong, nonatomic) NSFetchedResultsController *fetchedResultsController;
@property (readonly, strong, nonatomic) NSManagedObjectContext *objectContext;
@property (nonatomic, weak) id <LTCConcernViewModelDelegate>delegate;
- (instancetype)initWithContext:(NSManagedObjectContext *)context;
- (void)addConcern:(LTCConcern *)concern error:(NSError **)error;
- (void)removeConcern:(LTCConcern *)concern error:(NSError **)error;
- (LTCConcern *)concernAtIndexPath:(NSIndexPath *)indexPath;
- (NSUInteger )rowCountForSection:(NSUInteger)section;
@end
