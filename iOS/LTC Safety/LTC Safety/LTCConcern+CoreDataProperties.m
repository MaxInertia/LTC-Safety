//
//  LTCConcern+CoreDataProperties.m
//  
//
//  Created by Allan Kerr on 2017-01-27.
//
//

#import "LTCConcern+CoreDataProperties.h"

@implementation LTCConcern (CoreDataProperties)

+ (NSFetchRequest<LTCConcern *> *)fetchRequest {
	return [[NSFetchRequest alloc] initWithEntityName:@"LTCConcern"];
}

@dynamic actionsTaken;
@dynamic concernNature;
@dynamic reporter;
@dynamic location;

@end
