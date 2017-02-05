//
//  LTCRowValue.h 
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-02.
//  Copyright © 2017 Allan Kerr. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 The LTCRowValue class is used to parse the row values from the list of items within a value selection configuration plist. For every pre-defined selection value there is a single row value.
 */
@interface LTCRowValue : NSObject

/**
 The title of the row value that is displayed to the user.
 */
@property (readonly, nonatomic, copy) NSString *title;

/**
 The tag of the row value used to uniquely identify the row when tapped.
 */
@property (readonly, nonatomic, copy) NSString *tag;

/**
 Initializes a row value with a dictionary from within a value selection configuration plist.

 @pre dictionary != nil
 @attention The dictionary must have a string value for the key "title"
 @param dictionary The dictionary that the row value should be built from.
 @return An LTCRowValue created from parsing the dictionary.
 */
- (instancetype)initWithDictionary:(NSDictionary *)dictionary;

/**
 The designated initializer to initialize a row value with a tag. This is used for value selection options not loaded from the configuration file such as the other option for custom user input.

 @pre tag != nil
 @pre The tag must map to a string in the Localizable.strings to display for the user.
 @param tag The tag of the row value used to uniquely identify it within the value selection controller.
 @return An LTCRowValue created with a title from the tag.
 */
- (instancetype)initWithTag:(NSString *)tag;
@end
