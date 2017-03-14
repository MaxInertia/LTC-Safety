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

/**
 The LTC Concern Status class will represent a concern's current or past status. This status includes the creation date, and the status itself.
 */
@interface LTCConcernStatus : NSManagedObject
/**
 Creates a new concern status given a datastore concern status object and a context to save the status into.
 @pre           The data must be non-nil.
 @pre           The context must be non-nil.
 @param data    The datastore concern status data that will be used to create the local concern status.
 @param context The context that the local concern status will be saved into.

 @return The local concern status.
 */
+ (instancetype)statusWithData:(nonnull GTLRClient_ConcernStatus *)data inManagedObjectContext:(nonnull NSManagedObjectContext *)context;
@end

NS_ASSUME_NONNULL_END

#import "LTCConcernStatus+CoreDataProperties.h"
