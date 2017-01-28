//
//  LTCConcern+CoreDataClass.m
//  
//
//  Created by Allan Kerr on 2017-01-27.
//
//

#import "LTCConcern+CoreDataClass.h"
#import "LTCLocation+CoreDataClass.h"
#import "LTCReporter+CoreDataClass.h"

@interface LTCConcern ()
// Store the owner token to keychain on save and awake from fetch
- (void)didSave;
- (void)awakeFromFetch;
@end

@implementation LTCConcern

@end
