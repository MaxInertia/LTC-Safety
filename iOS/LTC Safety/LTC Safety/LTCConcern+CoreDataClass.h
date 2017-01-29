//
//  LTCConcern+CoreDataClass.h
//  
//
//  Created by Allan Kerr on 2017-01-27.
//
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>

@class LTCLocation, LTCReporter;

NS_ASSUME_NONNULL_BEGIN

@interface LTCConcern : NSManagedObject
@property (nonatomic, copy) NSString *ownerToken;
+ (instancetype)concernInManagedObjectContext:(NSManagedObjectContext *)context;
@end

NS_ASSUME_NONNULL_END

#import "LTCConcern+CoreDataProperties.h"
