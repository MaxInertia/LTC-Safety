//
//  LTCConcern+CoreDataProperties.h
//  
//
//  Created by Allan Kerr on 2017-01-27.
//
//

#import "LTCConcern+CoreDataClass.h"


NS_ASSUME_NONNULL_BEGIN

@interface LTCConcern (CoreDataProperties)

+ (NSFetchRequest<LTCConcern *> *)fetchRequest;

@property (nullable, nonatomic, copy) NSString *actionsTaken;
@property (nullable, nonatomic, copy) NSString *concernNature;
@property (nullable, nonatomic, retain) LTCReporter *reporter;
@property (nullable, nonatomic, retain) LTCLocation *location;

@end

NS_ASSUME_NONNULL_END
