//
//  LTCReporter+CoreDataProperties.m
//  
//
//  Created by Allan Kerr on 2017-01-27.
//
//

#import "LTCReporter+CoreDataProperties.h"

@implementation LTCReporter (CoreDataProperties)

+ (NSFetchRequest *)fetchRequest {
	return [[NSFetchRequest alloc] initWithEntityName:@"LTCReporter"];
}

@dynamic email;
@dynamic name;
@dynamic phoneNumber;
@dynamic report;

@end
