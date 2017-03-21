//
//  LTCConcernStatus+CoreDataProperties.h
//  
//
//  Created by Daniel Morris on 2017-03-20.
//
//

#import "LTCConcernStatus+CoreDataClass.h"


NS_ASSUME_NONNULL_BEGIN

@interface LTCConcernStatus (CoreDataProperties)

+ (NSFetchRequest<LTCConcernStatus *> *)fetchRequest;

@property (nullable, nonatomic, copy) NSString *concernType;
@property (nullable, nonatomic, copy) NSDate *creationDate;
@property (nullable, nonatomic, retain) LTCConcern *status;

@end

NS_ASSUME_NONNULL_END
