//
//  LTCConcernStatus+CoreDataProperties.h
//  
//
//  Created by Allan Kerr on 2017-02-01.
//
//

#import "LTCConcernStatus+CoreDataClass.h"


NS_ASSUME_NONNULL_BEGIN

@interface LTCConcernStatus (CoreDataProperties)

+ (NSFetchRequest *)fetchRequest;

@property (nullable, nonatomic, copy) NSString *concernType;
@property (nullable, nonatomic, copy) NSDate *creationDate;
@property (nullable, nonatomic, retain) LTCConcern *status;

@end

NS_ASSUME_NONNULL_END
