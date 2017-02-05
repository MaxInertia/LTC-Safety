//
//  LTCConcern+CoreDataClass.h
//  
//
//  Created by Allan Kerr on 2017-02-01.
//
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>
#import "LTCConcernStatus+CoreDataClass.h"
#import "LTCLocation+CoreDataClass.h"
#import "LTCReporter+CoreDataClass.h"
#import "GTLRClient.h"

@class LTCConcernStatus, LTCLocation, LTCReporter;

NS_ASSUME_NONNULL_BEGIN

/**
 The LTC Concern class will represent a person's safety concern. This concern includes all data shown in the LTCConcern+CoreDataProperties
 class.
 */
@interface LTCConcern : NSManagedObject
/**
 Creates a new concern given a backend datastore concern object with an ownerToken and a context to save the concern into.
 
 @pre              The concern must be non-nil.
 @pre              The context must be non-nil.
 @param data       The data that will be passed to create the local concern.
 @param ownerToken The ownerToken to be saved into the local concern that allows the client to have access to the data once it is in the datastore.
 @param context    The context that the local concern will be saved into.
 
 @return The local concern
 */
+ (instancetype)concernWithData:(nonnull GTLRClient_Concern *)data ownerToken:(NSString *)ownerToken inManagedObjectContext:(nonnull NSManagedObjectContext *)context;
@end

NS_ASSUME_NONNULL_END

#import "LTCConcern+CoreDataProperties.h"
