//
//  LTCLocation+CoreDataProperties.h
//  
//
//  Created by Allan Kerr on 2017-01-27.
//
//

#import "LTCLocation+CoreDataClass.h"


NS_ASSUME_NONNULL_BEGIN

@interface LTCLocation (CoreDataProperties)

+ (NSFetchRequest<LTCLocation *> *)fetchRequest;

@property (nullable, nonatomic, copy) NSString *facilityName;
@property (nullable, nonatomic, copy) NSString *roomName;
@property (nullable, nonatomic, retain) LTCConcern *location;

@end

NS_ASSUME_NONNULL_END
