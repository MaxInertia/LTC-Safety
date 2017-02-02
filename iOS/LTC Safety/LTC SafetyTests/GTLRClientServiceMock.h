//
//  GTLRClientServiceMock.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-01.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "GTLRClient.h"

@interface GTLRClientServiceMock : GTLRClientService
@property (nonatomic, assign) BOOL simulateError;
@end
