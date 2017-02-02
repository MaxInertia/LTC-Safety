//
//  LTCConcern+CoreDataClass.h
//  
//
//  Created by Allan Kerr on 2017-01-27.
//
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>
#import "LTCConcernStatus+CoreDataClass.h"
#import "LTCLocation+CoreDataClass.h"
#import "LTCReporter+CoreDataClass.h"
#import "GTLRClient.h"

@class LTCLocation, LTCReporter;

NS_ASSUME_NONNULL_BEGIN

@interface LTCConcern : NSManagedObject
@property (nonatomic, copy) NSString *ownerToken;
+ (instancetype)concernWithConcern:(GTLRClient_Concern *)concern;
@end

NS_ASSUME_NONNULL_END

#import "LTCConcern+CoreDataProperties.h"
