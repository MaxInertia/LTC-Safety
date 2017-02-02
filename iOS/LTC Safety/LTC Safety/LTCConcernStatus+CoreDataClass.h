//
//  LTCConcernStatus+CoreDataClass.h
//  
//
//  Created by Allan Kerr on 2017-02-01.
//
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>
#import "GTLRClient.h"

@class LTCConcern;

NS_ASSUME_NONNULL_BEGIN

@interface LTCConcernStatus : NSManagedObject
+ (instancetype)statusWithData:(nonnull GTLRClient_ConcernStatus *)data inManagedObjectContext:(nonnull NSManagedObjectContext *)context;
@end

NS_ASSUME_NONNULL_END

#import "LTCConcernStatus+CoreDataProperties.h"
