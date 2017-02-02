//
//  LTCConcern+CoreDataProperties.m
//  
//
//  Created by Allan Kerr on 2017-02-01.
//
//

#import "LTCConcern+CoreDataProperties.h"

@implementation LTCConcern (CoreDataProperties)

+ (NSFetchRequest<LTCConcern *> *)fetchRequest {
	return [[NSFetchRequest alloc] initWithEntityName:@"LTCConcern"];
}

@dynamic actionsTaken;
@dynamic concernNature;
@dynamic submissionDate;
@dynamic identifier;
@dynamic location;
@dynamic reporter;
@dynamic statuses;

@end
