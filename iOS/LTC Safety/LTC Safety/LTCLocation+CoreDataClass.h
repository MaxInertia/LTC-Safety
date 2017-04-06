//
//  LTCLocation+CoreDataClass.h
//  
//
//  Created by Allan Kerr on 2017-01-27.
//
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>
#import "GTLRClient.h"

@class LTCConcern;

NS_ASSUME_NONNULL_BEGIN

/**
 The LTC Location class will represent a concern's facility name and room number.
 
 History properties: Properties of this class should not vary from the time they are created.
 
 Invariance properties: This class makes no assumptions.
 
 */
@interface LTCLocation : NSManagedObject

/**
 Creates a new concern location given a datastore concern location object and a context to save the location into.
 @pre           The data must be non-nil.
 @pre           The context must be non-nil.
 @param data    The datastore concern location data that will be used to create the local concern location.
 @param context The context that the local concern location will be saved into.
 
 @return The local concern location.
 */
+ (instancetype)locationWithData:(nonnull GTLRClient_Location *)data inManagedObjectContext:(nonnull NSManagedObjectContext *)context;
@end

NS_ASSUME_NONNULL_END

#import "LTCLocation+CoreDataProperties.h"
