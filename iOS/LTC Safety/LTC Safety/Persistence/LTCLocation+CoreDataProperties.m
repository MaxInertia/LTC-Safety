//
//  LTCLocation+CoreDataProperties.m
//  
//
//  Created by Allan Kerr on 2017-01-27.
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
