//
//  LTCConcernViewModel.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-27.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "LTCConcern+CoreDataClass.h"

@interface LTCConcernViewModel : NSObject
@property (readonly, nonatomic, assign) NSUInteger sectionCount;
- (instancetype)initWithContext:(NSManagedObjectContext *)context;
- (void)addConcern:(LTCConcern *)concern;
- (LTCConcern *)concernAtIndexPath:(NSIndexPath *)indexPath;
- (NSUInteger )rowCountForSection:(NSUInteger)section;
- (void)removeConcern:(LTCConcern *)concern;
@end
