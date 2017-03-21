//
//  LTCReporter+CoreDataProperties.h
//  
//
//  Created by Daniel Morris on 2017-03-20.
//
//

#import "LTCReporter+CoreDataClass.h"


NS_ASSUME_NONNULL_BEGIN

@interface LTCReporter (CoreDataProperties)

+ (NSFetchRequest<LTCReporter *> *)fetchRequest;

@property (nullable, nonatomic, copy) NSString *email;
@property (nullable, nonatomic, copy) NSString *name;
@property (nullable, nonatomic, copy) NSString *phoneNumber;
@property (nullable, nonatomic, retain) LTCConcern *report;

@end

NS_ASSUME_NONNULL_END
