//
//  LTCLocation+CoreDataClass.m
//  
//
//  Created by Allan Kerr on 2017-01-27.
//
//

#import "LTCLocation+CoreDataClass.h"
#import "LTCConcern+CoreDataClass.h"

@implementation LTCLocation


+ (instancetype)locationWithData:(nonnull GTLRClient_Location *)data inManagedObjectContext:(nonnull NSManagedObjectContext *)context {
    
    NSAssert(data != nil, @"The location data must be non-null");
    NSAssert(context != nil, @"The managed object context must be non-null");
    
    NSEntityDescription *description = [NSEntityDescription entityForName:@"LTCLocation" inManagedObjectContext:context];
    return [[LTCLocation alloc] initWithData:data entity:description insertIntoManagedObjectContext:context];
}

/**
 Constructs the concern location given data passed through the locationWithData method.
 
 @param locationData    The datastore concern location data that will be passed to create the local concern location.
 @param entity  The entity description for this object.
 @param context The context object space this concern location will be stored in.
 
 @return The constructed LTCLocation
 */
- (instancetype)initWithData:(nonnull GTLRClient_Location *)locationData entity:(NSEntityDescription *)entity insertIntoManagedObjectContext:(NSManagedObjectContext *)context {
    if (self = [super initWithEntity:entity insertIntoManagedObjectContext:context]) {
        self.facilityName = locationData.facilityName;
        self.roomName = locationData.roomName;
    }
    return self;
}

@end
