//
//  LTCLocation+CoreDataProperties.m
//  
//
//  Created by Daniel Morris on 2017-03-20.
//
//

#import "LTCLocation+CoreDataProperties.h"

@implementation LTCLocation (CoreDataProperties)

+ (NSFetchRequest<LTCLocation *> *)fetchRequest {
	return [[NSFetchRequest alloc] initWithEntityName:@"LTCLocation"];
}

@dynamic facilityName;
@dynamic roomName;
@dynamic location;

@end
