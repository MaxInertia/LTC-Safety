//
//  LTCConcern+CoreDataProperties.h
//  
//
//  Created by Allan Kerr on 2017-02-01.
//
//

#import "LTCConcern+CoreDataClass.h"


NS_ASSUME_NONNULL_BEGIN

@interface LTCConcern (CoreDataProperties)

+ (NSFetchRequest<LTCConcern *> *)fetchRequest;

@property (nullable, nonatomic, copy) NSString *actionsTaken;
@property (nullable, nonatomic, copy) NSString *concernNature;
@property (nullable, nonatomic, copy) NSString *identifier;
@property (nullable, nonatomic, copy) NSDate *submissionDate;
@property (nullable, nonatomic, retain) LTCLocation *location;
@property (nullable, nonatomic, retain) LTCReporter *reporter;
@property (nullable, nonatomic, retain) NSOrderedSet<LTCConcernStatus *> *statuses;

@end

@interface LTCConcern (CoreDataGeneratedAccessors)

- (void)insertObject:(LTCConcernStatus *)value inStatusesAtIndex:(NSUInteger)idx;
- (void)removeObjectFromStatusesAtIndex:(NSUInteger)idx;
- (void)insertStatuses:(NSArray<LTCConcernStatus *> *)value atIndexes:(NSIndexSet *)indexes;
- (void)removeStatusesAtIndexes:(NSIndexSet *)indexes;
- (void)replaceObjectInStatusesAtIndex:(NSUInteger)idx withObject:(LTCConcernStatus *)value;
- (void)replaceStatusesAtIndexes:(NSIndexSet *)indexes withStatuses:(NSArray<LTCConcernStatus *> *)values;
- (void)addStatusesObject:(LTCConcernStatus *)value;
- (void)removeStatusesObject:(LTCConcernStatus *)value;
- (void)addStatuses:(NSOrderedSet<LTCConcernStatus *> *)values;
- (void)removeStatuses:(NSOrderedSet<LTCConcernStatus *> *)values;

@end

NS_ASSUME_NONNULL_END
